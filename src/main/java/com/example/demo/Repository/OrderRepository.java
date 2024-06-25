package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import com.example.demo.dto.ProductOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	void save(ProductOrderDTO productOrder);
	
	@Query("SELECT o FROM Order o WHERE o.orderCode LIKE 'S%'")
	Page<Order> findByOrderCodeStartingWithS(Pageable pageable);
	
	@Query("SELECT o FROM Order o WHERE o.orderCode LIKE 'P%'")
	Page<Order> findByOrderCodeStartingWithP(Pageable pageable);

}