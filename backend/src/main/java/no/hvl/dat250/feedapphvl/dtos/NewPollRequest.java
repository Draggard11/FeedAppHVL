package no.hvl.dat250.feedapphvl.dtos;
import java.util.List;

import no.hvl.dat250.feedapphvl.domain.PollOption;

public record NewPollRequest(
        String question,
        Long createdBy,
        List<PollOption> options
) {
}