package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.data.VoteRepository;
import se.yrgo.domain.Vote;
import se.yrgo.domain.VoteType;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class VoteRestController {

    @Autowired
    private VoteRepository voteRepository;

    @PostMapping("/{reviewId}/vote")
    public ResponseEntity<?> castVote(@PathVariable("reviewId") Long reviewId, @RequestBody Vote voteRequest) {
        try {
            Vote vote = new Vote(voteRequest.getVoterName(), voteRequest.getVoteType(), reviewId.intValue());
            voteRepository.save(vote);

            long voteCount = voteRepository.countUpvotesByReviewId(reviewId) + voteRepository.countDownvotesByReviewId(reviewId);
            return ResponseEntity.ok().body("{\"voteCount\": " + voteCount + "}");
        } catch (Exception e) {
            return new ResponseEntity<>("Error casting vote: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
