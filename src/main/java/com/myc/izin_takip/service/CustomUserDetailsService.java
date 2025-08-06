package com.myc.izin_takip.service;

import com.myc.izin_takip.model.Employee;
import com.myc.izin_takip.model.User;
import com.myc.izin_takip.repository.EmployeeRepository;
import com.myc.izin_takip.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        }

        Optional<Employee> empOpt = employeeRepository.findByTelefon(username);
        if (empOpt.isPresent()) {
            Employee employee = empOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    employee.getTelefon(),
                    employee.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + employee.getRole()))
            );
        }

        throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
    }
}
