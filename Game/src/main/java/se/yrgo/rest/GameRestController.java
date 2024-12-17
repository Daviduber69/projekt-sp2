package se.yrgo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.yrgo.data.GameRepository;
import se.yrgo.domain.Game;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameRestController {
    @Autowired
    private GameRepository gameRepository;

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

}
