package com.example.demo.Repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.Entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
        Staff findByEmail(String email);
        boolean existsByEmail(String email);  
        @Modifying
	    @Query("UPDATE Staff s SET s.password = :password WHERE s.email = :email")
	    @Transactional
	    void updatePasswordByEmail(String email, String password);          
}
