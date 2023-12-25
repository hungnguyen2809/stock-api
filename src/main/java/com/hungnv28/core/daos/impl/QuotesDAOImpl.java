package com.hungnv28.core.daos.impl;

import com.hungnv28.core.base.BaseDAO;
import com.hungnv28.core.daos.QuotesDAO;
import com.hungnv28.core.dtos.QuotesHistoricalDTO;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuotesDAOImpl extends BaseDAO implements QuotesDAO {

    @Qualifier("coreFactory")
    private final SessionFactory sessionFactory;

    @Override
    public List<QuotesHistoricalDTO> getHistorical(int stockId, String fromDate, String toDate) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("select AVG(QUOTES.PRICE), QUOTES.UPDATE_AT\n" +
                            "from QUOTES where STOCK_ID = :stockId and UPDATE_AT >= :fromDate and UPDATE_AT <= :toDate \n" +
                            "group by UPDATE_AT\n" +
                            "order by UPDATE_AT;", Object.class)
                    .setParameter("stockId", stockId)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate);

            List<Object[]> resutlList = query.getResultList();
            List<QuotesHistoricalDTO> dtoList = new ArrayList<>();

            for (Object[] objects : resutlList) {
                QuotesHistoricalDTO historicalDTO = new QuotesHistoricalDTO();
                historicalDTO.setPrice((BigDecimal) objects[0]);
                historicalDTO.setTime(DateTimeUtils.formatTimestamp((Timestamp) objects[1], DateTimeUtils.DDMMYYYY));
                dtoList.add(historicalDTO);
            }

            return dtoList;
        } catch (Exception e) {
            log.error("QuotesDAO_getHistorical {}", e.getMessage());
            throw new ApiException(e);
        }
    }
}
