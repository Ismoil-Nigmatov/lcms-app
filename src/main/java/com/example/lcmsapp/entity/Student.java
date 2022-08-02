package com.example.lcmsapp.entity;

import com.example.lcmsapp.entity.enums.GenderType;
import com.example.lcmsapp.entity.enums.StudentStatus;
import com.example.lcmsapp.entity.template.AbsEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@ToString
public class Student extends AbsEntity {
    @ManyToOne
    private Filial filial;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    private Double balance;

    private Date birth;

    private String phone;

    private String address;

    private String cardNumber;

    private String placeOfIssue;

    private String contractNumber;

    private Date contractDate;

    @ManyToMany
    private List<Course> course;

    @ManyToMany
    private List<Group> groups;

    @Enumerated(EnumType.STRING)
    private StudentStatus status;

}
