package com.hungnv28.core.services.impl;

import com.hungnv28.core.controllers.request.OrderCreateRequest;
import com.hungnv28.core.daos.OrderDAO;
import com.hungnv28.core.daos.StockDAO;
import com.hungnv28.core.dtos.OrderInfoDTO;
import com.hungnv28.core.exception.ApiException;
import com.hungnv28.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDAO orderDAO;
    private final StockDAO stockDAO;

    @Override
    public List<OrderInfoDTO> getOrder(long userId) throws Exception {
        return orderDAO.getOrder(userId);
    }

    @Override
    public boolean createOrder(long userId, OrderCreateRequest order) throws Exception {
        if (order.getQuantity() <= 0) {
            throw new ApiException("Số lượng phải lớn hơn 0", "quantity");
        }

        if (!stockDAO.isExistsStock(order.getStockId())) {
            throw new ApiException("Chứng khoán này không tồn tại", "not_exists_stock");
        }

        return orderDAO.createOrder(userId, order);
    }
}
