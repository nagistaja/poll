package ee.ut.demo.poll.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.ut.demo.poll.exception.NotFoundException;
import ee.ut.demo.poll.model.Poll;
import ee.ut.demo.poll.model.Response;
import ee.ut.demo.poll.model.PollUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import ee.ut.demo.poll.repository.extractros.PollListExtractor;
import ee.ut.demo.poll.repository.extractros.ResponsesListExtractor;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class PollRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UUID insert(PollUpdate poll) {
        final UUID uuid = UUID.randomUUID();
        jdbcTemplate.update("INSERT INTO poll (uuid, title, description, active, owner) VALUES (?, ?, ?, ?, ?)",
                uuid, poll.title, poll.description, poll.active, poll.owner);
        postOptions(poll, uuid);
        return uuid;
    }

    public void update(PollUpdate poll, UUID uuid) {
        jdbcTemplate.update("UPDATE poll p SET title = ?, description = ?, active = ?, owner = ? " +
                "WHERE p.uuid = ?", poll.title, poll.description, poll.active, poll.owner, uuid);
        jdbcTemplate.update("DELETE FROM poll_option po WHERE po.poll_uuid = ?", uuid);
        postOptions(poll, uuid);
    }

    public void update(Response response, UUID uuid) {
        ObjectMapper objectMapper = new ObjectMapper();
        String responsesString = null;
        try {
            responsesString = objectMapper.writeValueAsString(response.choices);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        int updatedRows = jdbcTemplate.update("UPDATE poll_response pr SET pr.responses = ? " +
                "WHERE pr.poll_uuid = ? and pr.owner = ?", responsesString, uuid, response.owner);
        if (updatedRows == 0) {
            jdbcTemplate.update("INSERT INTO poll_response (poll_uuid, owner, responses) " +
                    "VALUES (?, ?, ?);", uuid, response.owner, responsesString);
        }
    }

    public Poll getPoll(UUID uuid) {
        log.debug(uuid);
        var pollsMap = jdbcTemplate.query("SELECT p.uuid, p.title, p.description, p.active, p.owner, " +
                "po.order_nr, po.option FROM poll p " +
                "LEFT JOIN poll_option po ON p.uuid = po.poll_uuid " +
                "WHERE p.uuid= '" + uuid + "';", new PollListExtractor());
        log.debug(pollsMap.toString());
        Poll poll = pollsMap.get(uuid);
        if (poll == null) {
            log.debug("There is no poll with uuid={}", uuid);
            throw new NotFoundException("There is no poll with such uuid");
        }
        poll.setResponses(getResponses(uuid));
        return poll;
    }

    public List<Poll> getActivePolls() {
        List<Poll> polls = new ArrayList<>();
        Map<UUID, Poll> pollsMap = jdbcTemplate.query(
                "SELECT p.uuid, p.title, p.description, p.active, p.owner, " +
                "po.order_nr, po.option FROM poll p " +
                "LEFT JOIN poll_option po ON p.uuid = po.poll_uuid " +
                "WHERE p.active=true ", new PollListExtractor());
        for(Map.Entry<UUID, Poll> entry : pollsMap.entrySet()) {
            Poll poll = entry.getValue();
            poll.setResponses(getResponses(entry.getKey()));
            polls.add(poll);
        }
        return polls;
    }

    private void postOptions (PollUpdate poll, UUID uuid) {
        if (poll.options != null && !poll.options.isEmpty()) {
            final AtomicInteger order = new AtomicInteger(1);
            final List<Object[]> options = poll.options.stream()
                    .map(opt -> new Object[] { uuid, order.getAndIncrement(), opt })
                    .collect(Collectors.toList());
            jdbcTemplate.batchUpdate("INSERT INTO poll_option VALUES (?, ?, ?)", options);
        }
    }

    private List<Response> getResponses (UUID uuid) {
        return jdbcTemplate.query(
                "SELECT pr.owner,  pr.responses FROM poll p " +
                        "LEFT JOIN poll_response pr ON p.uuid = pr.poll_uuid " +
                        "WHERE p.uuid= '" + uuid + "';",
                new ResponsesListExtractor());
    }
}
