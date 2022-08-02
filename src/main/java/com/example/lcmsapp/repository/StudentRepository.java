package com.example.lcmsapp.repository;

import com.example.lcmsapp.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    Page<Student> findAllByFullNameContainingIgnoreCaseAndFilial_NameContainingIgnoreCase(String fullName, String filialName, Pageable pageable);

    Page<Student> findAllByBalance(Double balance,Pageable pageable);

    Optional<Student> findByFullNameContainingIgnoreCase(String student);
}
