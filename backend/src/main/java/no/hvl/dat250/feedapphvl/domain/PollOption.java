package no.hvl.dat250.feedapphvl.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Setter
    private String description;

    @Setter
    private int presentationOrder;

    //TODO implement lookup on votes -> get Voter and how many votes
    @OneToMany(mappedBy = "votedOn")

    private Set<Vote> votes;

    // default constructor
    public PollOption() {}


    public PollOption(String title, int presentationOrder) {
        this.title = title;
        this.presentationOrder = presentationOrder;
        this.votes = new LinkedHashSet<>();
    }
    /**
     *
     * @param voter the user that votes
     * @return the vote that
     */
    public Vote addVote(User voter) {
        return new Vote(voter, this);
    }

    public class Vote {
        private final User voter;
        private final PollOption poll;

        public Vote(User voter, PollOption option) {
            this.voter = voter;
            this.poll = option;
        }
    }
}
