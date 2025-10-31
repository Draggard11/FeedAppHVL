package no.hvl.dat250.feedapphvl.controllers;

import no.hvl.dat250.feedapphvl.domain.User;
import no.hvl.dat250.feedapphvl.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User u = new User(username, encodedPassword);
        userRepo.save(u);

        return ResponseEntity.ok("User created: " + username);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {


        return "login";
    }


}
