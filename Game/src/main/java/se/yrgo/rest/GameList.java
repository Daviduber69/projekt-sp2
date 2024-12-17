package se.yrgo.rest;

import se.yrgo.domain.Game;

import java.util.List;

public class GameList {
    private List<Game> games;

    public GameList(){}

    public GameList(List<Game> games){
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
