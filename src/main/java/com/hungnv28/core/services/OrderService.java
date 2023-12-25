package com.hungnv28.core.services;

import com.hungnv28.core.controllers.request.OrderCreateRequest;
import com.hungnv28.core.dtos.OrderInfoDTO;

import java.util.List;

public interface OrderService {
    List<OrderInfoDTO> getOrder(long userId) throws Exception;

    boolean createOrder(long userId, OrderCreateRequest orderCreateRequest) throws Exception;
}
