package no.hvl.dat250.feedapphvl.repositories;

import no.hvl.dat250.feedapphvl.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollsRepo extends CrudRepository<Poll, Long> {
}