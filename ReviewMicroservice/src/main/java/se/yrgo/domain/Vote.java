package se.yrgo.domain;

import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String voterName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;


    @Column(name = "game_id", nullable = false)
    private Integer reviewId;

    public Vote() {}

    public Vote(String voterName, VoteType voteType, int reviewId) {
        this.voterName = voterName;
        this.voteType = voteType;
        this.reviewId = reviewId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoterName() {
        return voterName;
    }

    public void setVoterName(String voterName) {
        this.voterName = voterName;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", voterName='" + voterName + '\'' +
                ", voteType=" + voteType +
                ", reviewId=" + reviewId +
                '}';
    }
}