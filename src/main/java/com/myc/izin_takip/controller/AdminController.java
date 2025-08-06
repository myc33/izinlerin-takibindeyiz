package com.myc.izin_takip.controller;

import com.myc.izin_takip.model.Employee;
import com.myc.izin_takip.model.LeaveRequest;
import com.myc.izin_takip.repository.EmployeeRepository;
import com.myc.izin_takip.repository.LeaveRequestRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public String showAddEmployeeForm(Model model) {
        List<Employee> managers = employeeRepository.findAll();
        model.addAttribute("managers", managers);
        return "add-employee"; // formun view ismi
    }
    @PostMapping("/add-employee")
    public String addEmployee(@RequestParam String ad,
                              @RequestParam String soyad,
                              @RequestParam String baslamaTarihi,
                              @RequestParam String telefon,
                              @RequestParam String password,
                              @RequestParam String unvan,
                              @RequestParam(required = false)  Long managerId) {

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
        employee.setUnvan(unvan);
        if (managerId != null) {
            Employee manager = employeeRepository.findById(managerId).orElse(null);
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }
        employee.setRemainingLeave(employee.hesaplaKalanIzin());
        employeeRepository.save(employee);

        return "redirect:/admin/employees";
    }

    @GetMapping("/employees")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee-list";
    }

    @PostMapping("/delete-employee")
    public String deleteEmployee(@RequestParam Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/admin/employees";
    }

    @GetMapping("/edit-employee/{id}")
    public String showEditEmployeeForm(@PathVariable Long id, Model model) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Geçersiz çalışan Id:" + id));
        List<Employee> managers = employeeRepository.findAll();
        model.addAttribute("employee", employee);
        model.addAttribute("managers", managers);
        return "edit-employee";
    }

    @PostMapping("/edit-employee")
    public String updateEmployee(@RequestParam Long id,
                                 @RequestParam String unvan,
                                 @RequestParam(required = false) Long managerId) {

        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Geçersiz çalışan Id:" + id));
        employee.setUnvan(unvan);

        if (managerId != null) {
            Employee manager = employeeRepository.findById(managerId).orElse(null);
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }

        employeeRepository.save(employee);
        return "redirect:/admin/employees";
    }


}
