package com.epam.esm.service.impl;

import com.epam.esm.util.ErrorCode;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dao.UserOrderDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserOrderService;
import com.epam.esm.model.util.DateTimeUtil;
import com.epam.esm.util.DtoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    private UserOrderDao userOrderDao;
    private UserDao userDao;
    private GiftCertificateDao giftCertificateDao;
    private ExceptionProvider exceptionProvider;

    @Autowired
    public UserOrderServiceImpl(UserOrderDao userOrderDao,
                                UserDao userDao,
                                GiftCertificateDao giftCertificateDao,
                                ExceptionProvider exceptionProvider) {
        this.userOrderDao = userOrderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
        this.exceptionProvider = exceptionProvider;
    }


    @Override
    public OrderDto add(long userId, long certificateId) {
        User user = userDao.findById(userId).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
        GiftCertificate giftCertificate = giftCertificateDao.findById(certificateId).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
        );
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setCost(giftCertificate.getPrice());
        order.setOrderDate(DateTimeUtil.getCurrentDateIso());
        return DtoWrapper.toOrderDto(userOrderDao.add(order));
    }

    @Override
    @Transactional
    public Optional<OrderDto> findUserOrderById(long userId, long orderId) {
        userDao.findById(userId).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
        return userOrderDao.findById(orderId).filter(o -> o.getUser().getId() == userId).map(DtoWrapper::toOrderDto);
    }

    @Override
    public List<OrderDto> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        return userOrderDao.findOrdersByUserId(userId, limit, offset)
                .stream()
                .map(DtoWrapper::toOrderDto)
                .collect(Collectors.toList());
    }
}
