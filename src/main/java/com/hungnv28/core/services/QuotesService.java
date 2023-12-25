package com.hungnv28.core.services;

import com.hungnv28.core.dtos.QuotesHistoricalDTO;

import java.util.List;

public interface QuotesService {
    List<QuotesHistoricalDTO> getHistorical(String stockId, String fromDate, String toDate) throws Exception;
}
