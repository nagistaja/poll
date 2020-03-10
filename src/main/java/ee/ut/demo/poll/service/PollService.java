package ee.ut.demo.poll.service;

import ee.ut.demo.poll.model.Poll;
import ee.ut.demo.poll.model.Response;
import ee.ut.demo.poll.model.PollUpdate;
import ee.ut.demo.poll.repository.PollRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class PollService {

    @Autowired
    private PollRepository repository;

    public List<Poll> getActivePolls() {
        return repository.getActivePolls();
    }

    public Poll getPoll(UUID uuid) {
        return repository.getPoll(uuid);
    }

    @Transactional
    public Poll createPoll(PollUpdate poll) {
        validatePoll(poll);
        final UUID uuid = repository.insert(poll);
        return getPoll(uuid);
    }

    @Transactional
    public Poll upatePoll(UUID uuid, PollUpdate poll) {
        validatePoll(poll);
        repository.update(poll, uuid);
        return getPoll(uuid);
    }

    @Transactional
    public Poll updatePollResponses(UUID pollUuid, Response response) {
        repository.update(response, pollUuid);
        return getPoll(pollUuid);
    }

    private void validatePoll(PollUpdate poll) {
        Assert.notNull(poll, "Poll must not be null");
        Assert.notNull(poll.title, "Poll title must not be null");
    }

}
