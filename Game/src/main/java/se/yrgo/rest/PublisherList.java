package se.yrgo.rest;

import se.yrgo.domain.Game;
import se.yrgo.domain.Publisher;

import java.util.List;

public class PublisherList {
    private List<Publisher> publishers;

    public PublisherList(){}

    public PublisherList(List<Publisher> publishers){
        this.publishers =publishers;
    }

    public List<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(List<Publisher> publishers) {
        this.publishers = publishers;
    }
}
