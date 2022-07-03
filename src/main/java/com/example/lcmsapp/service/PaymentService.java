package com.example.lcmsapp.service;


import com.example.lcmsapp.dto.ApiResponse;
import com.example.lcmsapp.dto.PaymentDto;
import com.example.lcmsapp.entity.Filial;
import com.example.lcmsapp.entity.Payment;
import com.example.lcmsapp.entity.Student;
import com.example.lcmsapp.entity.enums.PayType;
import com.example.lcmsapp.exception.ResourceNotFoundException;
import com.example.lcmsapp.repository.FilialRepository;
import com.example.lcmsapp.repository.PaymentRepository;
import com.example.lcmsapp.repository.StudentRepository;
import com.example.lcmsapp.util.DateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author "Husniddin Ulachov"
 * @created 2:57 PM on 6/26/2022
 * @project Edu-Center
 */
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;

    private final FilialRepository filialRepository;
    private final DateFormatUtil dateFormat;

    public ApiResponse<Payment> save(PaymentDto paymentDto) {

        Student student = studentRepository.findById(UUID.fromString(paymentDto.getStudentId())).orElseThrow(() -> new ResourceNotFoundException("student", "id", paymentDto.getStudentId()));

        Filial filial = filialRepository.findById(paymentDto.getFilialId()).orElseThrow(() -> new ResourceNotFoundException("filial", "id", paymentDto.getFilialId()));
        Payment payment = new Payment();
        payment.setStudent(student);
        payment.setFilial(filial);
        payment.setAmount(paymentDto.getAmount());

        for (PayType value : PayType.values()) {
            if (value.toString().equals(paymentDto.getPayType())) {
                payment.setPayType(value);
            }
        }
        return new ApiResponse<>("saved", paymentRepository.save(payment), true);
    }

    public ApiResponse<Payment> getOne(String id) {
        Payment payment = paymentRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("payment", "id", id));
        return new ApiResponse<>("GetOne", payment, true);
    }
    public ApiResponse<String> delete(String id) {
        paymentRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("payment", "id", id));
        paymentRepository.deleteById(UUID.fromString(id));
        return new ApiResponse<>("Deleted by id", id, true);
    }

    public ApiResponse<?> getAll(int page, int size, String filial, String student,
                                  String startDate, String endDate) {
        Page<Payment> payments = null;

        Pageable pageable = PageRequest.of(page, size);
//      hech nima kiritmagan holat
        if (filial.equals("") && student.equals("")  && startDate.equals("") && endDate.equals("")) {
            payments = paymentRepository.findAll(pageable);
        }
//     faqat vaqt oraligini kiritilgan holat
        else if (filial.equals("") && student.equals("")) {
            payments = paymentRepository.findAllByCreatedAtBetween(dateFormat.stringtoDate(startDate), dateFormat.stringtoDate(endDate), pageable);
        }
//    faqat student kiritilgan holat(vaqt bilan)
        else if (filial.equals("")) {
            studentRepository.findByFullNameContainingIgnoreCase(student).orElseThrow(() -> new ResourceNotFoundException("student ", " fullName  ?: ", student));
            payments = paymentRepository.findAllByCreatedAtBetweenOrStudent_FullName(dateFormat.stringtoDate(startDate), dateFormat.stringtoDate(endDate), student, pageable);
        }
//    faqat filial kiritilgan holat(vaqt bilan)
        else if (student.equals("")) {
            filialRepository.findByNameContainingIgnoreCase(filial).orElseThrow(() -> new ResourceNotFoundException("filial", "name ?:  ", filial));
            payments = paymentRepository.findAllByCreatedAtBetweenOrFilial_Name(dateFormat.stringtoDate(startDate), dateFormat.stringtoDate(endDate), student, pageable);
        }
//     filial va student kiritilgan hol
        else {
            filialRepository.findByNameContainingIgnoreCase(filial).orElseThrow(() -> new ResourceNotFoundException("filial", "name ?:  ", filial));
            studentRepository.findByFullNameContainingIgnoreCase(student).orElseThrow(() -> new ResourceNotFoundException("student ", " fullName  ?: ", student));
            payments = paymentRepository.findAllByCreatedAtBetweenOrStudent_FullNameAndFilial_Name(dateFormat.stringtoDate(startDate), dateFormat.stringtoDate(endDate), student, filial, pageable);
        }
        return ApiResponse.builder().data(payments).success(true).message("ok").build();
    }
}
