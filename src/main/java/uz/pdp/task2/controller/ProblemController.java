package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.ProblemDto;
import uz.pdp.task2.service.ProblemService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {
    @Autowired
    ProblemService problemService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ProblemDto problemDto){
        ApiResponse apiResponse = problemService.add(problemDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // READ
    @GetMapping
    public ResponseEntity<?> get(){
        List<Problem> problemList = problemService.get();
        return ResponseEntity.ok(problemList);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Problem problem = problemService.getById(id);
        return ResponseEntity.ok(problem);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody ProblemDto problemDto){
        ApiResponse apiResponse = problemService.edit(id, problemDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = problemService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:
                HttpStatus.CONFLICT).body(apiResponse);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
