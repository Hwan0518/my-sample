package com.uniongraphix.hexatest.adaptor.infrastructure.mysql.repository;

import com.uniongraphix.hexatest.adaptor.infrastructure.mysql.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    // 영속성 어뎁터에서 접근할 repository
    Optional<UsersEntity> findByEmail(String email);

}
