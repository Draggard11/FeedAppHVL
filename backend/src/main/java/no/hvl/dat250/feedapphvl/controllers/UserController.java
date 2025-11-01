package no.hvl.dat250.feedapphvl.controllers;

import no.hvl.dat250.feedapphvl.domain.Roles;
import no.hvl.dat250.feedapphvl.domain.User;
import no.hvl.dat250.feedapphvl.dtos.ErrorMsg;
import no.hvl.dat250.feedapphvl.dtos.NewUserRequest;
import no.hvl.dat250.feedapphvl.repositories.UserRepo;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

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
        if (userRepo.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("User with username '" + username + "' already exists"));
        }
        String encodedPassword = passwordEncoder.encode(password);
        User u = new User();
        u.setUsername(username);
        u.setPassword(encodedPassword);
        u.setRole(Roles.USER);
        userRepo.save(u);

        return ResponseEntity.ok("User created: " + username);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login( @RequestParam String username, @RequestParam String password, HttpServletRequest request) {

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        // store in SecurityContext so Spring creates a session
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true); // ensure session is created now

        var principal = (UserDetails) auth.getPrincipal();
        var roles = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(Map.of(
            "username", principal.getUsername(),
            "roles", roles
        ));
    }

}
