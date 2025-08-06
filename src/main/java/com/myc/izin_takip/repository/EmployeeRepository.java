package com.myc.izin_takip.repository;

import com.myc.izin_takip.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByTelefon(String telefon);
}
