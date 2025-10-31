package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Table (name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Setter
    private String username;

    @Setter
    private String email;

    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "createdBy")
    @JsonManagedReference(value = "created") @JsonIgnore
    private Set<Poll> created;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "voted") @JsonIgnore
    private Set<Vote> voted;
    // default constructor
    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.created = new LinkedHashSet<>();
        this.voted = new LinkedHashSet<>();
    }

    public Vote voteFor(PollOption option) {
        Vote v = new Vote(option);
        return v;
    }

    public Poll createPoll(String question) {
        Poll p = new Poll(question, this);
        this.created.add(p);
        return p;
    }

    public void voteOnOption(PollOption option) {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role);
    }
}
