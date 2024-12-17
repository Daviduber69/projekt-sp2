package se.yrgo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import se.yrgo.domain.Vote;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByReviewId(int reviewId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.reviewId = ?1 AND v.voteType = 'UPVOTE'")
    long countUpvotesByReviewId(Long reviewId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.reviewId = ?1 AND v.voteType = 'DOWNVOTE'")
    long countDownvotesByReviewId(Long reviewId);
}
