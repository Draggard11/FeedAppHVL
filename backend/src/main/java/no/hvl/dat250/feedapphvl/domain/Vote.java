package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne
    private PollOption pollOption;

    public Vote(PollOption option) {
        this.pollOption = option;
    }

    public Vote(){

    }



    @JsonIdentityReference(alwaysAsId = true)
    public PollOption getVotedOn() {
        return this.pollOption;
    }
}