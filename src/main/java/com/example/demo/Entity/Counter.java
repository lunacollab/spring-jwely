package com.example.demo.Entity;
import lombok.*;

import java.util.List;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int counterID;
    
    @Column(name="counterName")
    private String counterName;
    
    @Column(name="active")
    private boolean active;
    
    @OneToMany(mappedBy = "productID", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Product> product;

}
