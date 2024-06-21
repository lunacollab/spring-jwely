package com.example.demo.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.Entity.Staff;


public interface StaffService {
	Page<Staff> findAll(Pageable pageable);
	Staff save(Staff staff);
}
