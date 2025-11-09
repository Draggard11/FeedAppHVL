package no.hvl.dat250.feedapphvl.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainBasicTest {

    // ai generated tests just to have something for the pipeline 
    
    @Test
    void poll_constructor_sets_question_and_publishedAt() {
        var creator = new User();
        var poll = new Poll("Best backend?", creator);

        assertEquals("Best backend?", poll.getQuestion());
        assertNotNull(poll.getPublishedAt(), "publishedAt should be set on creation");
        assertTrue(poll.getPollOptions().isEmpty(), "new poll should start with 0 options");
    }

    @Test
    void poll_addOption_adds_in_order_and_titles_match() {
        var creator = new User();
        var poll = new Poll("Pick one", creator);

        var o1 = poll.addOption("Java");
        var o2 = poll.addOption("Kotlin");
        var o3 = poll.addOption("Go");

        assertEquals(3, poll.getPollOptions().size());
        assertEquals("Java", o1.getTitle());
        assertEquals("Kotlin", o2.getTitle());
        assertEquals("Go", o3.getTitle());

        // presentationOrder should be 0..n-1
        assertEquals(0, poll.getPollOptions().get(0).getPresentationOrder());
        assertEquals(1, poll.getPollOptions().get(1).getPresentationOrder());
        assertEquals(2, poll.getPollOptions().get(2).getPresentationOrder());
    }

    @Test
    void pollOption_voteCount_starts_at_zero_and_increments_when_vote_added() {
        var option = new PollOption("Yes", 0);

        assertEquals(0, option.getVoteCount(), "new option should have 0 votes");

        // Create a vote and add it to the inverse collection
        var v1 = new Vote(option);
        option.getVotes().add(v1);

        assertEquals(1, option.getVoteCount());

        var v2 = new Vote(option);
        option.getVotes().add(v2);

        assertEquals(2, option.getVoteCount());
    }

    @Test
    void user_createPoll_sets_creator_and_links_poll() {
        var u = new User();
        var p = new Poll("Q?", u);

        assertSame(u, p.getCreatedBy(), "poll.createdBy should point to the creator user");
    }


}
