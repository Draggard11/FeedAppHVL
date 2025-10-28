package no.hvl.dat250.feedapphvl.repositories;


import no.hvl.dat250.feedapphvl.domain.PollOption;
import org.springframework.data.repository.CrudRepository;


public interface PollOptionRepo extends CrudRepository<PollOption, Long> {
}