package ee.ut.demo.poll.web;

import ee.ut.demo.poll.model.Poll;
import ee.ut.demo.poll.model.Response;
import ee.ut.demo.poll.model.PollUpdate;
import ee.ut.demo.poll.service.PollService;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/polls", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
@Validated
public class PollController {

    @Autowired
    private PollService service;

    @GetMapping
    public List<Poll> getActivePolls(@RequestHeader("If-Modified-Since") Date date) {
        return service.getActivePolls();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Poll createPoll(@NotNull @RequestBody @Valid PollUpdate poll) {
        return service.createPoll(poll);
    }

    @GetMapping("{uuid}")
    public Poll getPoll(@PathVariable UUID uuid) {
        return service.getPoll(uuid);
    }

    @PostMapping(path = "{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Poll upatePoll(@PathVariable UUID uuid, @NotNull @RequestBody @Valid PollUpdate poll) {
        return service.upatePoll(uuid, poll);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "{uuid}/response", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Poll updatePollResponses(@PathVariable UUID uuid, @NotNull @RequestBody @Valid Response response) {
        return service.updatePollResponses(uuid, response);
    }

}
