package com.example.demo.Entity;

import javax.persistence.*;
import java.util.Date;
import lombok.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promotionID;

    @NotNull(message="Vui lòng không để trống trường này")
    private String promotionCode;

    private String promotionName;
    private float promotionPersent;
    private Date startDate;
    private Date endDate;
    private String applyFor;
}
