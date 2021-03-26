package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.CourseDto;
import uz.pdp.task2.service.CourseService;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> add( @Valid @RequestBody CourseDto courseDto){
        ApiResponse apiResponse = courseService.add(courseDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // READ
    @GetMapping
    public ResponseEntity<?> get(){
        List<Course> courseList = courseService.get();
        return ResponseEntity.ok(courseList);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Course course = courseService.getById(id);
        return ResponseEntity.ok(course);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody CourseDto courseDto){
        ApiResponse apiResponse = courseService.edit(id, courseDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = courseService.delete(id);
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
