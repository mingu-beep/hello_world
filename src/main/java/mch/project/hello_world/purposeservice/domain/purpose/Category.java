package mch.project.hello_world.purposeservice.domain.purpose;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    private String code;
    private String categoryName;
}
