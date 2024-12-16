package se.yrgo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.domain.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findByName(String name);

}
