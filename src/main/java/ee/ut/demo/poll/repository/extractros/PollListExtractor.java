package ee.ut.demo.poll.repository.extractros;

import ee.ut.demo.poll.model.Poll;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log4j2
public class PollListExtractor implements ResultSetExtractor<Map<UUID, Poll>> {

    @Override
    public Map<UUID, Poll> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<Poll> polls = new ArrayList<>();
        Map<UUID, Poll> pollsMap = new HashMap<>();
        while (rs.next()) {
            Poll poll;
            UUID uuid = UUID.fromString(rs.getString("uuid"));
            if (pollsMap.containsKey(uuid)) {
                poll = pollsMap.get(uuid);
                if (rs.getString("option") != null) {
                    poll.getOptions().add(rs.getString("option"));
                }
            } else {
                poll = new Poll();
                poll.setUuid(uuid);
                poll.setActive(rs.getBoolean("active"));
                poll.setTitle(rs.getString("title"));
                poll.setOptions(new ArrayList<>());
                if (rs.getString("option") != null) {
                    poll.getOptions().add(rs.getString("option"));
                }
                poll.setOwnerUserName(rs.getString("owner"));
                poll.setDescription(rs.getString("description"));
                poll.setResponses(new ArrayList<>());
                pollsMap.put(uuid, poll);
            }

        }
        for(Map.Entry<UUID, Poll> poll : pollsMap.entrySet()) {
            polls.add(poll.getValue());
        }
        return pollsMap;
    }
}
