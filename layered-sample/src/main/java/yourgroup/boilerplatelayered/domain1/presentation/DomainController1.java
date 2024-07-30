package yourgroup.boilerplatelayered.domain1.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yourgroup.boilerplatelayered.domain1.application.DomainService1;

@RestController
@RequestMapping("/api/v1/domain")
@RequiredArgsConstructor
public class DomainController1 {

    private final DomainService1 domainService1;
    private final ModelMapper modelMapper;

    // api controller 작성
}
