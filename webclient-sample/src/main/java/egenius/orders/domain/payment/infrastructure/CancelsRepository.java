package egenius.orders.domain.payment.infrastructure;

import egenius.orders.domain.payment.entity.Cancels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CancelsRepository extends JpaRepository<Cancels, Long> {
}
