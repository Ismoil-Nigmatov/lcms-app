package com.example.lcmsapp.controller;

import com.example.lcmsapp.dto.ApiResponse;
import com.example.lcmsapp.dto.TeacherDTO;
import com.example.lcmsapp.repository.TeacherRepository;
import com.example.lcmsapp.service.TeacherService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;


//    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TeacherDTO teacherDTO) {
        ApiResponse response = teacherService.add(teacherDTO);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

//    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOne(@PathVariable UUID uuid) {
        ApiResponse response = teacherService.getOne(uuid);
        return ResponseEntity.status(response.isSuccess() ? 200 : 404).body(response);
    }

//    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(teacherService.getAll());
    }

//    @PreAuthorize(value = "hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("{uuid}")
    public ResponseEntity<?> update(@PathVariable UUID uuid,@RequestBody TeacherDTO teacherDTO) {
        ApiResponse update = teacherService.update(uuid, teacherDTO);
        return ResponseEntity.status(update.isSuccess() ? 200 : 409).body(update);
    }

//    @PreAuthorize(value = "hasAnyRole('ADMIN')")
    @DeleteMapping("{uuid}")
    public ResponseEntity<?> delete(@PathVariable UUID uuid){
        ApiResponse delete = teacherService.delete(uuid);
        return ResponseEntity.status(delete.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
