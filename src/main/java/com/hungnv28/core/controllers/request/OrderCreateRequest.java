package com.hungnv28.core.controllers.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderCreateRequest implements Serializable {
    private int stockId;
    private String orderType;
    private String direction;
    private int quantity;
    private BigDecimal price;
    private String status;
}
