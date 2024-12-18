package se.yrgo.data;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.AddressEntity;


public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
