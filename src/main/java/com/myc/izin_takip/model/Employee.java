package com.myc.izin_takip.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ad;
    private String soyad;
    private LocalDate baslamaTarihi;
    private String telefon;
    private String password;
    private String role;
    private String username;

    // --- Getter & Setter'lar ---

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getAd() { return ad; }

    public void setAd(String ad) { this.ad = ad; }

    public String getSoyad() { return soyad; }

    public void setSoyad(String soyad) { this.soyad = soyad; }

    public LocalDate getBaslamaTarihi() { return baslamaTarihi; }

    public void setBaslamaTarihi(LocalDate baslamaTarihi) { this.baslamaTarihi = baslamaTarihi; }

    public String getTelefon() { return telefon; }

    public void setTelefon(String telefon) { this.telefon = telefon; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}
