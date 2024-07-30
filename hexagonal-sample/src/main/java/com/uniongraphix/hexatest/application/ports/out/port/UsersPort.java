package com.uniongraphix.hexatest.application.ports.out.port;

import com.uniongraphix.hexatest.domain.Users;

public interface UsersPort { // Domain과 PersistenceAdapter를 연결하는 Out Port이다

    // 영속성 어뎁터에서 db에 접근할 때 사용할 메소드
    Users signInUsers(Users users);
    Users signUpUsers(Users users);

}
