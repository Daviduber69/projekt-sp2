package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import se.yrgo.data.GameRepository;
import se.yrgo.data.PublisherRepository;
import se.yrgo.domain.Game;
import se.yrgo.domain.Publisher;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameRestController {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PublisherRepository publisherRepository;

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
    @RequestMapping(value = "/publisher", method = RequestMethod.POST)
    public ResponseEntity createPublisher(@RequestBody Publisher publisher){
        publisherRepository.save(publisher);
        return new ResponseEntity<>(publisher, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/publisher", method = RequestMethod.GET)
    public PublisherList allPublishers(){
        List<Publisher> publishers = publisherRepository.findAll();
        return new PublisherList(publishers);
    }
}
