package egenius.orders.domain.payment.infrastructure;

import egenius.orders.domain.payment.entity.VirtualAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirtualAccountRepository extends JpaRepository<VirtualAccount, Long> {
}
