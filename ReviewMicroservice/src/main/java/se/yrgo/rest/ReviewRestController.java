/*package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.data.ReviewRepository;
import se.yrgo.domain.Review;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping(value="/game/{gameId}/reviews", method = RequestMethod.POST)
    public ResponseEntity<?> createReview(@PathVariable("gameId") int gameId, @RequestBody Review review) {
        review.setGameId(gameId);
        reviewRepository.save(review);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @RequestMapping(value="/game/{gameId}/reviews", method = RequestMethod.GET)
    public ResponseEntity<?> getReviewsForGame(@PathVariable("gameId") int gameId) {
        List<Review> reviews = reviewRepository.findByGameId(gameId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @RequestMapping(value="/game/{gameId}/reviews/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReviewById(@PathVariable("gameId") int gameId, @PathVariable("id") Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
}*/

package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import se.yrgo.data.ReviewRepository;
import se.yrgo.data.VoteRepository;
import se.yrgo.domain.Game;
import se.yrgo.domain.Review;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/reviews", method = RequestMethod.GET)
    public ReviewList allGames(){
        List<Review> reviews = reviewRepository.findAll();
        return new ReviewList(reviews);
    }

    @RequestMapping(value = "/game/{name}/reviews", method = RequestMethod.POST)
    public ResponseEntity<?> createReview(@PathVariable("name") String name, @RequestBody Review review) {
        try {
            review.setVoteCount(0);
            Integer gameId = getGameIdFromGameService(name);
            if (gameId == null) {
                return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
            }

            review.setGameId(gameId);


            reviewRepository.save(review);
            return new ResponseEntity<>(review, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error creating review: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/game/{name}/reviews", method = RequestMethod.GET)
    public ResponseEntity<?> getReviewsForGame(@PathVariable("name") String name) {
        Integer gameId = getGameIdFromGameService(name);
        if (gameId == null) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        List<Review> reviews = reviewRepository.findByGameId(gameId);
        for (Review review : reviews) {
            long upvotes = voteRepository.countUpvotesByReviewId(review.getId());
            long downvotes = voteRepository.countDownvotesByReviewId(review.getId());
            int totalVoteCount = (int) (upvotes - downvotes);
            review.setVoteCount(totalVoteCount);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @RequestMapping(value = "/game/{name}/reviews/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReviewById(@PathVariable("name") String name, @PathVariable("id") Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
        long upvotes = voteRepository.countUpvotesByReviewId(review.getId());
        long downvotes = voteRepository.countDownvotesByReviewId(review.getId());
        int totalVoteCount = (int) (upvotes - downvotes);
        review.setVoteCount(totalVoteCount);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    private Integer getGameIdFromGameService(String gameName) {
        try {
            String gameServiceUrl = "http://localhost:8080/game/" + gameName;
            Map<String, Object> game = restTemplate.getForObject(gameServiceUrl, Map.class);
            if (game != null && game.get("id") != null) {
                Integer gameId = (Integer) game.get("id");
                return gameId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
