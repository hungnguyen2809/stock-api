package com.hungnv28.core.daos.impl;

import com.hungnv28.core.base.BaseDAO;
import com.hungnv28.core.controllers.request.OrderCreateRequest;
import com.hungnv28.core.daos.OrderDAO;
import com.hungnv28.core.dtos.OrderInfoDTO;
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
public class OrderDAOImpl extends BaseDAO implements OrderDAO {

    @Qualifier("coreFactory")
    private final SessionFactory sessionFactory;

    @Override
    public List<OrderInfoDTO> getOrder(long userId) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("SELECT ORDER_ID, STOCK_ID, ORDER_TYPE, DIRECTION, QUANTITY, PRICE, STATUS, ORDER_DATE\n" +
                            "FROM ORDERS where USER_ID = :userId", Object.class)
                    .setParameter("userId", userId);

            List<Object[]> list = query.getResultList();
            List<OrderInfoDTO> dtoList = new ArrayList<>();

            for (Object[] objects : list) {
                OrderInfoDTO dto = new OrderInfoDTO();
                dto.setOrderId((Integer) objects[0]);
                dto.setStockId((Integer) objects[1]);
                dto.setOrderType((String) objects[2]);
                dto.setDirection((String) objects[3]);
                dto.setQuantity((Integer) objects[4]);
                dto.setPrice((BigDecimal) objects[5]);
                dto.setStatus((String) objects[6]);
                dto.setOrderDate(DateTimeUtils.formatTimestamp((Timestamp) objects[7], DateTimeUtils.DDMMYYYY));
                dtoList.add(dto);
            }

            return dtoList;
        } catch (Exception e) {
            log.error("OrderDAO_getOrder: {}", e.getMessage());
            throw new ApiException(e);
        }
    }

    @Override
    public boolean createOrder(long userId, OrderCreateRequest order) throws Exception {
        try {
            Session session = getSession(sessionFactory);
            NativeQuery query = session.createNativeQuery("INSERT INTO ORDERS (USER_ID, STOCK_ID, ORDER_TYPE, DIRECTION, QUANTITY, PRICE, STATUS, ORDER_DATE)" +
                            " VALUES (:userId, :stockId :orderType, :direction, :price, :status, CURRENT_TIMESTAMP)", Object.class)
                    .setParameter("userId", userId)
                    .setParameter("stockId", order.getStockId())
                    .setParameter("orderType", order.getOrderType())
                    .setParameter("direction", order.getDirection())
                    .setParameter("price", order.getPrice())
                    .setParameter("status", order.getStatus());

            return query.executeUpdate() > 0;
        } catch (Exception e) {
            log.error("OrderDAO_createOrder: {}", e.getMessage());
            throw new ApiException(e);
        }
    }
}
