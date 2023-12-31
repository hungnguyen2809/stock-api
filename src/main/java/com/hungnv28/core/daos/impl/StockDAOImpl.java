package com.hungnv28.core.daos.impl;

import com.hungnv28.core.base.BaseDAO;
import com.hungnv28.core.daos.StockDAO;
import com.hungnv28.core.dtos.CoveredWarrantDTO;
import com.hungnv28.core.dtos.StockInfoDTO;
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
public class StockDAOImpl extends BaseDAO implements StockDAO {

    @Qualifier("coreFactory")
    private final SessionFactory sessionFactory;

    @Override
    public List<StockInfoDTO> watchList(long userId) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("SELECT S.STOCK_ID,\n" +
                            "       S.SYMBOL,\n" +
                            "       S.COMPANY_NAME,\n" +
                            "       S.MARKET_CAP,\n" +
                            "       S.SECTOR,\n" +
                            "       S.INDUSTRY,\n" +
                            "       S.SECTOR_EN,\n" +
                            "       S.INDUSTRY_EN,\n" +
                            "       S.STOCK_TYPE,\n" +
                            "       S.RANK_STOCK,\n" +
                            "       S.RANK_SOURCE,\n" +
                            "       S.REASON\n" +
                            "FROM (SELECT DISTINCT * FROM WATCH_LISTS WHERE WATCH_LISTS.USER_ID = :userId) AS WL\n" +
                            "         INNER JOIN STOCKS AS S ON WL.STOCK_ID = S.STOCK_ID", Object.class)
                    .setParameter("userId", userId);

            List<Object[]> resultList = query.getResultList();
            List<StockInfoDTO> listStock = new ArrayList<>();

            for (Object[] object : resultList) {
                StockInfoDTO stock = new StockInfoDTO();
                stock.setStockId((int) object[0]);
                stock.setSymbol((String) object[1]);
                stock.setCompanyName((String) object[2]);
                stock.setMarketCap((BigDecimal) object[3]);
                stock.setSector((String) object[4]);
                stock.setIndustry((String) object[5]);
                stock.setSectorEn((String) object[6]);
                stock.setIndustryEn((String) object[7]);
                stock.setStockType((String) object[8]);
                stock.setRankStock((int) object[9]);
                stock.setRankSource((String) object[10]);
                stock.setReason((String) object[11]);
                listStock.add(stock);
            }

            return listStock;
        } catch (Exception e) {
            log.error("StockDAO_watchList: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public boolean isExistsStock(int stockId) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery queryCheckStock = session.createNativeQuery("SELECT COUNT(1) FROM STOCKS WHERE STOCK_ID = :stockId", Object.class)
                    .setParameter("stockId", stockId);

            return ((Number) queryCheckStock.getSingleResult()).intValue() > 0;
        } catch (Exception e) {
            log.error("StockDAO_checkStock: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public boolean addWatchList(long userId, int stockId) throws Exception {
        try {
            Session session = getSession(sessionFactory);

            NativeQuery queryCheckExists = session.createNativeQuery("SELECT COUNT(1) FROM WATCH_LISTS WHERE STOCK_ID = :stockId AND USER_ID = :userId", Object.class)
                    .setParameter("userId", userId)
                    .setParameter("stockId", stockId);

            if (((Number) queryCheckExists.getSingleResult()).intValue() != 0) {
                throw new ApiException("Chứng khoán này đã được thêm", "exists_stock");
            }

            NativeQuery queryInsert = session.createNativeQuery("INSERT INTO WATCH_LISTS (USER_ID, STOCK_ID) VALUES (:userId, :stockId)", Object.class)
                    .setParameter("userId", userId)
                    .setParameter("stockId", stockId);

            return queryInsert.executeUpdate() > 0;
        } catch (Exception e) {
            log.error("StockDAO_addWatchList: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public List<CoveredWarrantDTO> listWarrant(int stockId) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("SELECT WARRANT_ID, NAME, ISSUE_DATE, EXPIRATION_DATE, PRICE_STRIKE, WARRANT_TYPE\n" +
                            "FROM COVERED_WARRANTS WHERE UNDERLYING_ASSET_ID = :stockId", Object.class)
                    .setParameter("stockId", stockId);

            List<Object[]> resultList = query.getResultList();
            List<CoveredWarrantDTO> dtoList = new ArrayList<>();

            for (Object[] object : resultList) {
                CoveredWarrantDTO stock = new CoveredWarrantDTO();
                stock.setWarrantId((int) object[0]);
                stock.setWarrantName((String) object[1]);
                stock.setIssueDate(DateTimeUtils.formatTimestamp((Timestamp) object[2], DateTimeUtils.DDMMYYYY));
                stock.setExpDate(DateTimeUtils.formatTimestamp((Timestamp) object[3], DateTimeUtils.DDMMYYYY));
                stock.setPriceStrike((BigDecimal) object[4]);
                stock.setWarrantType((String) object[5]);
                dtoList.add(stock);
            }

            return dtoList;
        } catch (Exception e) {
            log.error("StockDAO_listWarrant: {}", e.getMessage());
            throw new ApiException(e);
        }
    }
}
