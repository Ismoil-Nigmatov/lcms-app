package com.example.lcmsapp.dto;

import com.example.lcmsapp.entity.enums.StudentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResStudentDto {
    private String fullName;
    private String phone;
    private Double balance;
    private StudentStatus status;
    private String filialName;
    private List<String> courseName;
    private List<String> groupName;
}
