package kdh.batchtest.domain.test.infrastructure.jpa;


import kdh.batchtest.domain.test.infrastructure.entity.WinEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WinJpaRepository extends JpaRepository<WinEntity, Long> {

	Page<WinEntity> findByWinGreaterThanEqual(Long win, Pageable pageable);

}
