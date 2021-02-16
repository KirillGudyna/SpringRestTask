package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.service.UserOrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ErrorCode;
import com.epam.esm.util.HateoasData;
import com.epam.esm.validation.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private UserOrderService userOrderService;
    private ExceptionProvider exceptionProvider;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    @GetMapping
    public List<UserDto> findAll(@RequestParam(required = false) Integer limit,
                                 @RequestParam(required = false) Integer offset) {
        List<UserDto> users = userService.findAll(limit, offset);
        return users.stream()
                .map(this::addUserLinks)
                .collect(Collectors.toList());
    }


    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public UserDto findById(@PathVariable long id) {
        UserDto user = userService.findById(id).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.USER_NOT_FOUND)
        );
        return addUserLinks(user);
    }

    @PostMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public OrderDto buyCertificate(@PathVariable long userId, @RequestBody long certificateId) {
        if (!GiftEntityValidator.correctId(certificateId)) {
            throw exceptionProvider.wrongParameterFormatException(ErrorCode.BUY_PARAMETERS_WRONG_FORMAT);
        }
        OrderDto addedOrder = userOrderService.add(userId, certificateId);
        return addOrderLinks(addedOrder);
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public List<OrderDto> findUserOrders(@PathVariable long userId,
                                         @RequestParam(required = false) Integer limit,
                                         @RequestParam(required = false) Integer offset) {
        List<OrderDto> orders = userOrderService.findOrdersByUserId(userId, limit, offset);
        return orders.stream().map(this::addOrderLinks).collect(Collectors.toList());
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders/{orderId:^[1-9]\\d{0,18}$}")
    public OrderDto findUserOrder(@PathVariable long userId,
                                  @PathVariable long orderId) {
        Optional<OrderDto> optional = userOrderService.findUserOrderById(userId, orderId);
        return optional.map(this::addOrderLinks).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.ORDER_NOT_FOUND)
        );
    }

    @GetMapping("/widely-used-tag")
    public TagDto widelyUsedTag() {
        TagDto tag = userService.mostWidelyUsedTagOfUserWithHighestOrdersSum().orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ErrorCode.TAG_NOT_FOUND)
        );
        return TagController.addLinks(tag);
    }

    private OrderDto addOrderLinks(OrderDto order) {
        Long userId = order.getUser().getId();
        Long orderId = order.getId();
        GiftCertificateController.addSelfLink(order.getGiftCertificate());
        addUserLinks(order.getUser());
        return order
                .add(linkTo(methodOn(UserController.class).findUserOrder(userId, orderId)).withSelfRel());
    }

    private UserDto addUserLinks(UserDto user) {
        return user
                .add(linkTo(UserController.class).slash(user.getId()).withSelfRel())
                .add(linkTo(UserController.class).slash(user.getId()).slash(HateoasData.ORDERS)
                        .withRel(HateoasData.GET)
                        .withName(HateoasData.GET_USER_ORDERS))
                .add(linkTo(UserController.class).slash(user.getId()).slash(HateoasData.ORDERS)
                        .withRel(HateoasData.POST)
                        .withName(HateoasData.ADD_ORDER));
    }
}
