package yourgroup.boilerplatelayered.domain1.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yourgroup.boilerplatelayered.domain1.entity.DomainEntity1;

@Repository
public interface DomainRepository1 extends JpaRepository<DomainEntity1, Long> {
}