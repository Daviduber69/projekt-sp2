package se.yrgo.data;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yrgo.domain.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findByName(String name);
}
