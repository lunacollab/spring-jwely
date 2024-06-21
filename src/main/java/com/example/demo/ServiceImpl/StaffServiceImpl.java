package com.example.demo.ServiceImpl;

import com.example.demo.Repository.StaffRepository;
import com.example.demo.Service.StaffService;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Staff;
@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepository staffRepository;
	@Autowired
	private  BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public StaffServiceImpl (StaffRepository staffRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.staffRepository = staffRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;

	}
	   @Override
	    public Page<Staff> findAll(Pageable pageable) {
	        return staffRepository.findAll(pageable);
	    }
	  
	   
	  @Override
	  @Transactional
	    public Staff save(Staff staff) {
		   if (staffRepository.existsByEmail(staff.getEmail())) {
	            throw new IllegalArgumentException("Email '" + staff.getEmail() + "' is already in use.");
	        }else {
		        staff.setPassword(bCryptPasswordEncoder.encode(staff.getPassword()));
		        staff.setActive(true);
		        return staffRepository.save(staff);
	        }
	    }
}
