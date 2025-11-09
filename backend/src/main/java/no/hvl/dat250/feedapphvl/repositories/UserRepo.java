package no.hvl.dat250.feedapphvl.repositories;


import no.hvl.dat250.feedapphvl.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepo extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}