package yourgroup.boilerplatelayered.domain2.presentation;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yourgroup.boilerplatelayered.domain2.application.DomainService2;

@RestController
@RequestMapping("/api/v1/domain")
@RequiredArgsConstructor
public class DomainController2 {

    private final DomainService2 domainService2;
    private final ModelMapper modelMapper;

    // api controller 작성
}
