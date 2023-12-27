package com.hungnv28.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoveredWarrantDTO implements Serializable {
    private int warrantId;
    private String warrantName;
    private String warrantType;
    private BigDecimal priceStrike;
    private String expDate;
    private String issueDate;
}
