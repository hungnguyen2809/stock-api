package com.hungnv28.core.services.impl;

import com.hungnv28.core.daos.QuotesDAO;
import com.hungnv28.core.dtos.QuotesHistoricalDTO;
import com.hungnv28.core.services.QuotesService;
import com.hungnv28.core.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuotesServiceImpl implements QuotesService {

    private final QuotesDAO quotesDAO;

    @Override
    public List<QuotesHistoricalDTO> getHistorical(String stockId, String fromDate, String toDate) throws Exception {
        fromDate = DateTimeUtils.parseDate(fromDate, DateTimeUtils.DDMMYYYY).format(DateTimeUtils.YYYYMMDD);
        toDate = DateTimeUtils.parseDate(toDate, DateTimeUtils.DDMMYYYY).format(DateTimeUtils.YYYYMMDD);
        return quotesDAO.getHistorical(Integer.parseInt(stockId), fromDate, toDate);
    }
}
