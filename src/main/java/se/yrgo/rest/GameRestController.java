package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.yrgo.data.GameRepository;
import se.yrgo.data.ReviewRepository;
import se.yrgo.domain.Game;
import se.yrgo.domain.Review;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameRestController {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public GameList allGames(){
        List<Game> games = gameRepository.findAll();
        return new GameList(games);
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity createNewGame(@RequestBody Game game){
        gameRepository.save(game);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @RequestMapping(value="/game/{name}", method = RequestMethod.GET)
    public ResponseEntity gameByName(@PathVariable("name") String name){
        Game game = gameRepository.findByName(name);
        return ResponseEntity.ok(game);
    }

    @RequestMapping(value="/game/{name}/reviews", method = RequestMethod.POST)
    public ResponseEntity<?> createReview(@PathVariable("name") String name, @RequestBody Review review){
        Game game = gameRepository.findByName(name);
        if (game == null) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        review.setGame(game);
        reviewRepository.save(review);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @RequestMapping(value="/game/{name}/reviews", method = RequestMethod.GET)
    public ResponseEntity<?> getReviewsForGame(@PathVariable("name") String name){
        Game game = gameRepository.findByName(name);
        if (game == null) {
            return new ResponseEntity<>("Game not found", HttpStatus.NOT_FOUND);
        }
        List<Review> reviews = reviewRepository.findByGame(game);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @RequestMapping(value="/game/{name}/reviews/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getReviewById(@PathVariable("name") String name, @PathVariable("id") Long id){
    Review review = reviewRepository.findById(id).orElse(null);
    if (review == null) {
        return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(review, HttpStatus.OK);
}
}
