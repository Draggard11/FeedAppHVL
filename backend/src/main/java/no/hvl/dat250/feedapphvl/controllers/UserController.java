package no.hvl.dat250.feedapphvl.controllers;

import no.hvl.dat250.feedapphvl.domain.Roles;
import no.hvl.dat250.feedapphvl.domain.User;
import no.hvl.dat250.feedapphvl.dtos.ErrorMsg;
import no.hvl.dat250.feedapphvl.dtos.NewUserRequest;
import no.hvl.dat250.feedapphvl.repositories.UserRepo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/login")
    public String login() {return "login";}

    @GetMapping("/register")
    public String register() {return "register";}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NewUserRequest user) {
        if (userRepo.findByUsername(user.username()).isPresent()) {
            return ResponseEntity.badRequest().body(new ErrorMsg("User with username '" + user.username() + "' already exists"));
        }
        String encodedPassword = passwordEncoder.encode(user.password());
        User u = new User();
        u.setUsername(user.username());
        u.setEmail(user.email());
        u.setPassword(encodedPassword);
        u.setRole(Roles.USER);
        userRepo.save(u);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User created: " + user.username()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody NewUserRequest user, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.password(), user.password())
        );


        // build and store context
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        // ensure session exists
        request.getSession(true);

        // SAVE the context to session so it's available on the next request
        securityContextRepository.saveContext(context, request, response);
        var principal = (UserDetails) auth.getPrincipal();
        var roles = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "username", principal.getUsername(),
            "roles", roles
        ));
    }

    //kun for http testing
    @GetMapping("/me")
    public Map<String, Object> me() {
        var a = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        return Map.of(
            "name", a == null ? null : a.getName(),
            "auth", a == null ? null : a.getAuthorities(),
            "authenticated", a != null && a.isAuthenticated()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        var session = request.getSession(false);
        if (session != null) session.invalidate();
        org.springframework.security.core.context.SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out"));
    }

}
