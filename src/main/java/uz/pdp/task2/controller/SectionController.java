package uz.pdp.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.ApiResponse;
import uz.pdp.task2.payload.SectionDto;
import uz.pdp.task2.service.SectionService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/section")
public class SectionController {
    @Autowired
    SectionService sectionService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody SectionDto sectionDto){
        ApiResponse apiResponse = sectionService.add(sectionDto);
        return ResponseEntity.status(apiResponse.isSuccess()? HttpStatus.CREATED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // READ
    @GetMapping
    public ResponseEntity<?> get(){
        List<Section> sectionList = sectionService.get();
        return ResponseEntity.ok(sectionList);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Section section = sectionService.getById(id);
        return ResponseEntity.ok(section);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody SectionDto sectionDto){
        ApiResponse apiResponse = sectionService.edit(id, sectionDto);
        return ResponseEntity.status(apiResponse.isSuccess()?HttpStatus.ACCEPTED:
                HttpStatus.CONFLICT).body(apiResponse);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse = sectionService.delete(id);
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
