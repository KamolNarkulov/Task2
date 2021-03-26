package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
// Worker
public class InputDto {
    @NotNull(message = "code bo'sh bolmasligi kerak!")
    private String code;

    @NotNull(message = "score bo'sh bolmasligi kerak!")
    private Integer score;

    @NotNull(message = "problemId bo'sh bolmasligi kerak!")
    private Integer problemId;

    @NotNull(message = "userId bo'sh bolmasligi kerak!")
    private Integer userId;
}
