package no.hvl.dat250.feedapphvl.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
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

    @OneToMany(mappedBy = "votedOn", fetch = FetchType.LAZY)
    private Set<Vote> votes = new LinkedHashSet<>();

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
