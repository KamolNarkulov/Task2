package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.UserDto;
import uz.pdp.task2.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.add(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // READ
    @GetMapping
    public ResponseEntity<?> get(){
        List<User> userList = userService.get();
        return ResponseEntity.ok(userList);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.edit(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = userService.delete(id);
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
