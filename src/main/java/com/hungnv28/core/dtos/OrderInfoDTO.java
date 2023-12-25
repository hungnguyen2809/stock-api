package com.hungnv28.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoDTO implements Serializable {
    private int orderId;
    private int stockId;
    private String orderType;
    private String direction;
    private int quantity;
    private BigDecimal price;
    private String status;
    private String orderDate;
}
