package se.yrgo.rest;

import se.yrgo.domain.Review;

import java.util.List;

public class ReviewList {
    private List<Review> reviews;

    public ReviewList(){}

    public ReviewList(List<Review> reviews){
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
