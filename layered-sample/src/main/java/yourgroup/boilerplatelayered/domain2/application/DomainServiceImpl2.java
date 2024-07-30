package yourgroup.boilerplatelayered.domain2.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yourgroup.boilerplatelayered.domain2.infrastructure.DomainRepository2;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl2 implements DomainService2 {

    private final DomainRepository2 domainRepository2;

    // service 로직 작성

}
