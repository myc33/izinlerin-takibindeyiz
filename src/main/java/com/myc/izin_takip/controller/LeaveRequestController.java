package com.myc.izin_takip.controller;

import com.myc.izin_takip.model.Employee;
import com.myc.izin_takip.model.LeaveRequest;
import com.myc.izin_takip.repository.EmployeeRepository;
import com.myc.izin_takip.repository.LeaveRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class LeaveRequestController {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    // Constructor injection
    public LeaveRequestController(LeaveRequestRepository leaveRequestRepository,
                                  EmployeeRepository employeeRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/leave-request")
    public String submitLeaveRequest(@ModelAttribute LeaveRequest leaveRequest, Principal principal) {
        String username = principal.getName();
        Employee employee = employeeRepository.findByTelefon(username)
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));

        leaveRequest.setEmployee(employee);

        long days = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;
        leaveRequest.setDaysRequested(days);

        leaveRequest.setStatus("PENDING");

        leaveRequestRepository.save(leaveRequest);  // Burada hata olmaz

        return "redirect:/leave-request-success";
    }

    @GetMapping("/leave-request")
    public String showLeaveRequestForm(Model model) {
        model.addAttribute("leaveRequest", new LeaveRequest());

        List<String> leaveTypes = List.of(
                "Yıllık ücretli izin",
                "Doğum izni",
                "Evlenme izni",
                "Babalık izni",
                "Ölüm izni",
                "Engelli çocuk tedavisi izni",
                "Periyodik kontrol izni",
                "Süt izni",
                "Evlat edinme izni",
                "Yeni iş arama izni",
                "Mazeret izni",
                "Yarım gün doğum izni"
        );
        model.addAttribute("leaveTypes", leaveTypes);

        return "leave-request-form";
    }

    @GetMapping("/leave-request-success")
    public String leaveRequestSuccess() {
        return "leave-request-success";
    }
}
