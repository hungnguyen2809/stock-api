package com.hungnv28.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotesHistoricalDTO implements Serializable {
    private String time;
    private BigDecimal price;
}
