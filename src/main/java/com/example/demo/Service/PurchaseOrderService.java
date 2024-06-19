package com.example.demo.Service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.PurchaseOrderDTO;

public interface PurchaseOrderService {
	void savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);
}
