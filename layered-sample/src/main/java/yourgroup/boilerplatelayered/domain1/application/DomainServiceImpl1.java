package yourgroup.boilerplatelayered.domain1.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yourgroup.boilerplatelayered.domain1.infrastructure.DomainRepository1;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl1 implements DomainService1 {

    private final DomainRepository1 domainRepository;

    // service 로직 작성

}
