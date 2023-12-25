package com.hungnv28.core.daos;

import com.hungnv28.core.controllers.request.OrderCreateRequest;
import com.hungnv28.core.dtos.OrderInfoDTO;

import java.util.List;

public interface OrderDAO {
    List<OrderInfoDTO> getOrder(long userId) throws Exception;

    boolean createOrder(long userId, OrderCreateRequest orderCreateRequest) throws Exception;
}
