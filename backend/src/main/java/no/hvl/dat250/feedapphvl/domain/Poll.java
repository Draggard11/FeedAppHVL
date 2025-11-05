package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polls")
@Getter
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Version
    private Instant publishedAt;

    @Setter
    private Instant validUntil;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "pollOptions")
    private List<PollOption> pollOptions;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "created")
    private User createdBy;

    public Poll(String question, User createdBy) {
        this.question = question;
        this.createdBy = createdBy;
        this.pollOptions = new ArrayList<>();
        this.publishedAt = Instant.now();
    }

    public Poll() {
    }

    public PollOption addOption(String caption) {
        PollOption o = new PollOption(caption, this.pollOptions.size());
        this.pollOptions.add(o);
        return o;
    }

}