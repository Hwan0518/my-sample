package yourgroup.boilerplatelayered.domain1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Dto1 {

    // dto field 작성
    private String name;
}