package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "option_id", nullable = false)
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