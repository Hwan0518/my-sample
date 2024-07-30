package com.uniongraphix.hexatest.adaptor.infrastructure.mysql.persistance;

import com.uniongraphix.hexatest.adaptor.infrastructure.mysql.entity.UsersEntity;
import com.uniongraphix.hexatest.adaptor.infrastructure.mysql.repository.UsersRepository;
import com.uniongraphix.hexatest.application.ports.out.port.UsersPort;
import com.uniongraphix.hexatest.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsersAdaptor implements UsersPort { // out Port를 구현한 Driven Adapter

    private final UsersRepository usersRepository; // DB에 접근하는 영속성 어뎁터이므로, repository를 주입받는다

    @Override
    public Users signInUsers(Users users) {
        Optional<UsersEntity> usersEntity = usersRepository.findByEmail(users.getEmail());
        if(usersEntity.isPresent()){
            // entity를 다시 domain으로 바꾸는 과정 -> domain에 존재하는 도메인로직이 사용됨
            // db에서 조회한 usersEntity에서 email과 password를 사용하여 Users라는 도메인을 만든다
            return Users.signInUsers(usersEntity.get().getEmail(), usersEntity.get().getPassword());
        }
        return null;
    }

    @Override
    public Users signUpUsers(Users users) {
        // Entity를 생성하는 로직. Entity에서 정의된 엔티티 로직이다
        // Domain에서 가져온 정보로 Enitty를 생성한다
        UsersEntity usersEntity = UsersEntity.signUpUsers(
                users.getEmail(),
                users.getPassword(),
                users.getName(),
                users.getPhone(),
                users.getAddress()
                );
        usersRepository.save(usersEntity);
        return Users.formUsersEntity(usersEntity); // domain을 entity로 변경시켜주는 로직 -> 사실 이부분은 return값을 void로하고 반환 안해도 됨
    }
}
