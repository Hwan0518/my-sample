package egenius.orders.domain.payment.infrastructure;

import egenius.orders.domain.payment.entity.EasyPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EasyPayRepository extends JpaRepository<EasyPay, Long> {
}
