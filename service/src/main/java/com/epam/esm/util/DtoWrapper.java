package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.stream.Collectors;


public class DtoWrapper {
    private DtoWrapper(){
    }

    public static TagDto toTagDto(Tag tag){
        return new TagDto(tag.getId(), tag.getName());
    }

    public static GiftCertificateDto toGiftCertificateDto(GiftCertificate certificate){
        return new GiftCertificateDto(certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getCreateDate(),
                certificate.getLastUpdateDate(),
                certificate.getTags()
                        .stream()
                        .map(DtoWrapper::toTagDto).
                        collect(Collectors.toList())
        );
    }

    public static UserDto toUserDto(User user){
        return new UserDto(user.getId(), user.getName(), user.getSecondName(), user.getEmail(), user.getRole());
    }

    public static OrderDto toOrderDto(Order order){
        return new OrderDto(order.getId(),
                toUserDto(order.getUser()),
                toGiftCertificateDto(order.getGiftCertificate()),
                order.getOrderDate(),
                order.getCost());
    }
}
