package mch.project.hello_world.purposeservice.domain.purpose;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Importance {
    private String code;
    private String importanceName;
}
