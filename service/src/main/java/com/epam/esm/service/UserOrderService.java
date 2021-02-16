package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface UserOrderService {
    OrderDto add(long userId, long certificateId);

    Optional<OrderDto> findUserOrderById(long userId, long orderId);

    List<OrderDto> findOrdersByUserId(long userId, Integer limit, Integer offset);
}
