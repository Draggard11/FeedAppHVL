package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne
    private PollOption votedOn;

    public Vote(PollOption option) {
        this.votedOn = option;
    }

    public Vote(){

    }



    @JsonIdentityReference(alwaysAsId = true)
    public PollOption getVotedOn() {
        return this.votedOn;
    }
}