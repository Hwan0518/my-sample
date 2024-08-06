package kdh.batchtest.domain.test.infrastructure.jpa;


import kdh.batchtest.domain.test.infrastructure.entity.AfterEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AfterJpaRepository extends JpaRepository<AfterEntity, Long> {
}