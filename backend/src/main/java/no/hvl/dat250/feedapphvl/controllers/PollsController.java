package no.hvl.dat250.feedapphvl.controllers;

import no.hvl.dat250.feedapphvl.domain.Poll;
import no.hvl.dat250.feedapphvl.domain.PollOption;
import no.hvl.dat250.feedapphvl.domain.User;
import no.hvl.dat250.feedapphvl.domain.Vote;
import no.hvl.dat250.feedapphvl.repositories.PollOptionRepo;
import no.hvl.dat250.feedapphvl.repositories.PollsRepo;
import no.hvl.dat250.feedapphvl.repositories.UserRepo;
import no.hvl.dat250.feedapphvl.repositories.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import no.hvl.dat250.feedapphvl.dtos.ErrorMsg;
import no.hvl.dat250.feedapphvl.dtos.NewPollRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/polls")
@RestController
public class PollsController {

    @Autowired
    private PollsRepo pollsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PollOptionRepo pollOptionRepo;

    @Autowired
    private VoteRepo voteRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String POLL_TOPIC = "polls-events";

    @GetMapping()
    public Iterable<Poll> allPolls() {
        return pollsRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Poll> pollById( @PathVariable Long id) {
        return pollsRepo.findById(id);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<?> createPoll(@RequestBody NewPollRequest request) {
        Optional<User> maybeUser = userRepo.findById(request.createdBy());
        if (maybeUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("User with id '" + request.createdBy() + "' does not exist"));
        }
        User u = maybeUser.get();
        Poll p = u.createPoll(request.question());
        List<PollOption> options = new ArrayList<>();
        for (String o : request.options()) {
            options.add(p.addOption(o));
        }
        p = pollsRepo.save(p);
        kafkaTemplate.send(POLL_TOPIC, "new poll created: " + p.getQuestion());
        return ResponseEntity.created(URI.create("/polls/" + p.getId())).body(p);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        if (pollsRepo.findById(pollId).isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("Poll with id '" + pollId + "' does not exist"));
        }
        pollsRepo.deleteById(pollId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/votes")
    @Transactional
    public ResponseEntity<?> createPoll(@PathVariable Long id, @RequestParam Integer option, @RequestParam Long userId) {
        Optional<Poll> maybePoll = pollsRepo.findById(id);
        if (maybePoll.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("Poll with id '" + id + "' does not exist"));
        }
        Optional<User> maybeUser = userRepo.findById(userId);
        if (maybeUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("User with id '" + userId + "' does not exist"));
        }
        Poll poll = maybePoll.get();
        if (option < 0 || option >= poll.getPollOptions().size()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("Poll with id  '" + id + "' does not have an option with index/order '" + option + "'!"));
        }
        PollOption pollOption = poll.getPollOptions().get(option);
        Vote vote = maybeUser.get().voteFor(pollOption);
        voteRepo.save(vote);
        return ResponseEntity.ok().build();
    }


}