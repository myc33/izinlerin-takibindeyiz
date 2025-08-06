package com.myc.izin_takip.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDate startDate;
    private LocalDate endDate;
    private double daysRequested;  // Kaç gün izin talep edildi
    private String status; // Örn: PENDING, APPROVED, REJECTED
    private String leaveType;  // İzin tipi


    // Getter ve Setterlar

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getDaysRequested() { return daysRequested; }

    public void setDaysRequested(double daysRequested) { this.daysRequested = daysRequested; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
}
