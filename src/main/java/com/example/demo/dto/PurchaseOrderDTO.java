package com.example.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderDTO {
	private String diamondCode;
    private float carat;
    private String clarity;
    private String color;
    private String cut;
    private String gemCode;
    private String origin;
    
    private Integer typeGold;
    private String materialName;
    
    private float weight;
    private float diamondPrice;
    private float goldPrice;

    private String orderDate;
    private String staffName;
    private String customerName;
    private String phoneNumber;
}
