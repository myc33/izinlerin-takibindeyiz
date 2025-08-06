package com.myc.izin_takip.controller;

import com.myc.izin_takip.model.Employee;
import com.myc.izin_takip.repository.EmployeeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminController(EmployeeRepository employeeRepository, BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/add-employee")
    public String showAddEmployeeForm() {
        return "add-employee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@RequestParam String ad,
                              @RequestParam String soyad,
                              @RequestParam String baslamaTarihi,
                              @RequestParam String telefon,
                              @RequestParam String password) {

        Employee employee = new Employee();
        employee.setAd(ad);
        employee.setSoyad(soyad);
        employee.setBaslamaTarihi(LocalDate.parse(baslamaTarihi));
        employee.setTelefon(telefon);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setUsername(telefon); // telefon numarasını username olarak kullan
        if (employee.getRole() == null) {
            employee.setRole("EMPLOYEE");
        }

        employeeRepository.save(employee);

        return "redirect:/admin/employees";
    }

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

}
