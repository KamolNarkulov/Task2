package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Input;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.InputDto;
import uz.pdp.task2.service.InputService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/input")
public class InputController {
    @Autowired
    InputService inputService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody InputDto inputDto){
        ApiResponse apiResponse = inputService.add(inputDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // READ
    @GetMapping
    public ResponseEntity<?> get(){
        List<Input> inputList = inputService.get();
        return ResponseEntity.ok(inputList);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Input input = inputService.getById(id);
        return ResponseEntity.ok(input);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody InputDto inputDto){
        ApiResponse apiResponse = inputService.edit(id, inputDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = inputService.delete(id);
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
