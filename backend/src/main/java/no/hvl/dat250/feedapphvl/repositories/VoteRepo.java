package no.hvl.dat250.feedapphvl.repositories;

import no.hvl.dat250.feedapphvl.domain.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VoteRepo extends CrudRepository<Vote, UUID> {
}