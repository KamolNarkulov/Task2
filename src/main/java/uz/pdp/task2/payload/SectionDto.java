package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Company
public class SectionDto {
    @NotNull(message = "name bo'sh bolmasligi kerak!")
    private String name;

    @NotNull(message = "description bo'sh bolmasligi kerak!")
    private String description;

    @NotNull(message = "courseId bo'sh bolmasligi kerak!")
    private Integer courseId;
}
