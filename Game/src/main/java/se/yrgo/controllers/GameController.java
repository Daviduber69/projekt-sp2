package se.yrgo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import se.yrgo.data.GameRepository;
import se.yrgo.domain.Game;

import java.util.List;

@Controller
@RequestMapping("/website/games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @RequestMapping(value = "/newGame", method = RequestMethod.POST)
    public String newGame(@RequestParam Game game){
        gameRepository.save(game);
        return "redirect:/website/games/list.html";
    }

    @RequestMapping(value="/deleteGame.html", method = RequestMethod.POST)
    public String deleteGame(@RequestParam Long id){
        gameRepository.deleteById(id);
        return "redirect:/website/games/list.html";
    }
    @RequestMapping(value="/list.html", method = RequestMethod.GET)
    public ModelAndView allGames(){
        List<Game> allGames = gameRepository.findAll();
        return new ModelAndView("allGames", "games", allGames);
    }
    @RequestMapping(value="/game/{name}", method = RequestMethod.GET)
    public ModelAndView gameByName(@PathVariable("name") String name){
        Game game = gameRepository.findByName(name);
        return new ModelAndView("gameInfo", "game", game);
    }

}
