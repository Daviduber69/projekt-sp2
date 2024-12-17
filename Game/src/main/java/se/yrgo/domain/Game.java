package se.yrgo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    String name;
    String genre;
    String developer;
    int price;

    public Game(){}

    public Game(String name, String genre, String developer, int price) {
        this.name = name;
        this.genre = genre;
        this.developer = developer;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String toString(){
        return String.format("Game: %s%nGenre: %s%nDeveloper: %s%nPrice: %d%n" ,name, genre, developer, price);
    }
}
