package se.yrgo.domain;

import jakarta.persistence.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
   private String name;
   private String genre;
   private int price;
   @ManyToOne
   private Publisher publisher;

    public Game(){}

    public Game(String name, String genre, Publisher publisher, int price) {
        this.name = name;
        this.genre = genre;
        this.publisher = publisher;
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

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String toString(){
        return String.format("Game: %s%nGenre: %s%nDeveloper: %s%nPrice: %d%n" ,name, genre, publisher, price);
    }
}
