package yourgroup.boilerplatelayered.domain2.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yourgroup.boilerplatelayered.domain2.entity.DomainEntity2;

@Repository
public interface DomainRepository2 extends JpaRepository<DomainEntity2, Long> {
}