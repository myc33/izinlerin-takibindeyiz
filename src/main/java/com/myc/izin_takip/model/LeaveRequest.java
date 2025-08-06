package com.myc.izin_takip.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class LeaveRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Employee employee;

    private LocalDateTime startDate;  // Saatli tarih
    private LocalDateTime endDate;

    private double daysRequested;  // Kullanıcı girecek

    private String status;
    private String leaveType;


    // Getter ve Setterlar

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public double getDaysRequested() { return daysRequested; }
    public void setDaysRequested(double daysRequested) { this.daysRequested = daysRequested; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }
}
