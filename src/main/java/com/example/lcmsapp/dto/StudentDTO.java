package com.example.lcmsapp.dto;

import com.example.lcmsapp.entity.enums.GenderType;
import com.example.lcmsapp.entity.enums.StudentStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentDTO {
    @NotNull(message = "Filial tanlash shart")
    private Long filialId;
    @NotNull(message = "FIO to'ldirish shart")
    private String fullName;
    @NotNull(message = "")
    private GenderType gender;
    @NotNull(message = "Tug'ilgan sanani to'ldirish shart")
    private Date birth;
    @NotNull(message = "Telefon nomer berish shart")
    private String phone;
    @NotNull(message = "Address to'ldirish shart")
    private String address;
    @NotNull(message = "Passport seriya berish shart ")
    private String cardNumber;
    @NotNull(message = "")
    private String placeOfIssue;
    @NotNull(message = "Shartnoma to'ldirish shart")
    private String contractNumber;
    @NotNull(message = "")
    private Date contractDate;
    @NotNull(message = "Course larni tanlash shart")
    private List<Long> courseIds;
    @NotNull(message = "Gruppa tanlash shart")
    private List<Long> groupIds;
    private StudentStatus status;
}
