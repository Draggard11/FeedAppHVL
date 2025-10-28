package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;
    @Setter
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "createdBy")
    @JsonManagedReference(value = "created")
    private Set<Poll> created;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "voted")
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
}
