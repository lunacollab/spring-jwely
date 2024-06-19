package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.PurchaseOrderService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PurchaseOrderDTO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerService customerService;

	@Override
	public void savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO) {
		Order order = new Order();
		int quantity = 0;
		double total = (double)( purchaseOrderDTO.getDiamondPrice()+purchaseOrderDTO.getGoldPrice());
		order.setTotal(total);
		
		if (!purchaseOrderDTO.getMaterialName().isBlank())
		{
			quantity+=1;
		}
		if (!purchaseOrderDTO.getDiamondCode().isBlank())
		{
			quantity+=1;
		}
		order.setQuantity(quantity);
		order.setStaffID(7);
		order.setOrderstatusID(1);
		order.setDate(new Date());
		order.setType(true);
		
		int lpoitn = 0;
		String phone = purchaseOrderDTO.getPhoneNumber();
		String name = purchaseOrderDTO.getCustomerName();
		Customer cus = customerService.insertOrUpdateCustomer(phone, name, lpoitn);
		order.setCustomerID(cus.getCustomerID());

		orderRepository.save(order);

	}
}
