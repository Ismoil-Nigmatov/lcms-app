package com.example.lcmsapp.controller;

import com.example.lcmsapp.dto.ApiResponse;
import com.example.lcmsapp.dto.StudentDTO;
import com.example.lcmsapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<?> addStudent(@Valid @RequestBody StudentDTO studentDTO){
        ApiResponse response=studentService.add(studentDTO);
        return ResponseEntity.status(response.isSuccess()? 201:409).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAllStudent(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "") String search,
                                           @RequestParam(value = "filial", defaultValue = "") String filialName
//                                           @RequestParam(value = "-1") Double balance
                                           ){
        ApiResponse response=studentService.getAll(page,size,search,filialName
//                ,balance
                );
        return ResponseEntity.status(response.isSuccess()?HttpStatus.FOUND:HttpStatus.CONFLICT).body(response);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOneStudent(@PathVariable UUID uuid){
        ApiResponse response=studentService.getOne(uuid);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable UUID id){
        ApiResponse response=studentService.delete(id);
        return ResponseEntity.status(response.isSuccess()? HttpStatus.OK:HttpStatus.NOT_FOUND).body(response);
    }

    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{uuid}")
    public ResponseEntity<?> editStudent(@PathVariable UUID uuid,@Valid @RequestBody StudentDTO studentDTO){
        ApiResponse response=studentService.edit(uuid,studentDTO);
        return ResponseEntity.status(response.isSuccess()? 200:404).body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> e = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            e.put(fieldName, errorMessage);
        });
        return e;
    }
}
