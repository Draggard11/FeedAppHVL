package no.hvl.dat250.feedapphvl.security;

import no.hvl.dat250.feedapphvl.domain.User;
import no.hvl.dat250.feedapphvl.repositories.UserRepo;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo Repo;

    public CustomUserDetailsService(UserRepo Repo) {
        this.Repo = Repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Repo.findByUsername(username);
        return user.orElseThrow();
    }
}
