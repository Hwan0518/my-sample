package kdh.batchtest.domain.test.infrastructure.jpa;

import kdh.batchtest.domain.test.infrastructure.entity.BeforeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BeforeJpaRepository extends JpaRepository<BeforeEntity, Long> {
}
