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

    public Integer getVoteCount() {
        if(this.votes.isEmpty()){
            return 0;
        }else return this.votes.size();
    }

}
