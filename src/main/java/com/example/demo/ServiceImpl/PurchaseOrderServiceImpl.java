package com.example.demo.ServiceImpl;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Gem;
import com.example.demo.Entity.Material;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.PurchaseDetail;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.GemService;
import com.example.demo.Service.MaterialService;
import com.example.demo.Service.PurchaseOrderService;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PurchaseOrderDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
    private GemService gemService; 
	  
	@Autowired
	private MaterialService materialService; 

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
		List<PurchaseDetail> purchaseDetails = new ArrayList<>();
	        if (purchaseOrderDTO.getDiamondCode() != null && !purchaseOrderDTO.getDiamondCode().isEmpty()) {
	            Gem gem = gemService.getGemByGemCode(purchaseOrderDTO.getDiamondCode());
	            if (gem != null) {
	                PurchaseDetail gemDetail = new PurchaseDetail();
	                gemDetail.setGem(gem);
	                gemDetail.setGemPrice(purchaseOrderDTO.getDiamondPrice());
	                gemDetail.setProductName("Diamond"); 
	                gemDetail.setOrder(order);
	                purchaseDetails.add(gemDetail);
	            }
	        }
	        if (purchaseOrderDTO.getMaterialName() != null && !purchaseOrderDTO.getMaterialName().isEmpty()) {
	            Material material = materialService.getMaterialByName(purchaseOrderDTO.getMaterialName());
	            if (material != null) {
	                PurchaseDetail materialDetail = new PurchaseDetail();
	                materialDetail.setMaterial(material);
	                materialDetail.setMaterialPrice(purchaseOrderDTO.getGoldPrice());
	                materialDetail.setWeight(purchaseOrderDTO.getWeight());
	                materialDetail.setProductName("Material"); 
	                materialDetail.setOrder(order);
	                purchaseDetails.add(materialDetail);
	            }
	        }

	    order.setPurchaseDetails(purchaseDetails);
	    orderRepository.save(order);

	}
}
