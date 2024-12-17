package se.yrgo.domain;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String reviewerName;

    @Column(nullable = false)
    private String reviewContent;

    @Column(nullable = false)
    private int rating;

    @Column(name = "game_id", nullable = false)
    private Integer gameId;

    @Column(nullable = false)
    private Integer voteCount = 0;

    public Review() {}

    public Review(String reviewerName, String reviewContent, int rating, int gameId) {
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.rating = rating;
        this.gameId = gameId;
        this.voteCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return String.format(
                "Reviewer: %s%nRating: %d/5%nReview: %s%nGame: %d%nVoteCount: %d%n",
                reviewerName, rating, reviewContent, gameId, voteCount
        );
    }
}