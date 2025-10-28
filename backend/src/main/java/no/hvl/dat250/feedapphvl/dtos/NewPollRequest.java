package no.hvl.dat250.feedapphvl.dtos;
import java.util.List;

public record NewPollRequest(
        String question,
        Long createdBy,
        List<String> options
) {
}