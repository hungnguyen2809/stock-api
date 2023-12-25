package com.hungnv28.core.daos;

import com.hungnv28.core.dtos.QuotesHistoricalDTO;

import java.util.List;

public interface QuotesDAO {
    List<QuotesHistoricalDTO> getHistorical(int stockId, String fromDate, String toDate) throws Exception;
}
