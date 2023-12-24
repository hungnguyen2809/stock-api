package com.hungnv28.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockInfoDTO implements Serializable {
    private int stockId;
    private String symbol;
    private String companyName;
    private BigDecimal marketCap;
    private String sector;
    private String industry;
    private String sectorEn;
    private String industryEn;
    private String stockType;
    private int rankStock;
    private String rankSource;
    private String reason;
}
