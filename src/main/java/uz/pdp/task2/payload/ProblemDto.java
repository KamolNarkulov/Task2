package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Department
public class ProblemDto {
    @NotNull(message = "title bo'sh bolmasligi kerak!")
    private String title;

    @NotNull(message = "body bo'sh bolmasligi kerak!")
    private String body;

    @NotNull(message = "solution bo'sh bolmasligi kerak!")
    private String solution;

    @NotNull(message = "sectionId bo'sh bolmasligi kerak!")
    private Integer sectionId;
}
