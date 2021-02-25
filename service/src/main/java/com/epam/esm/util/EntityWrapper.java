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

public class EntityWrapper {
    private EntityWrapper(){
    }

    public static Tag toTag(TagDto tagDto){
        return new Tag(tagDto.getId(), tagDto.getName());
    }

    public static GiftCertificate toGiftCertificate(GiftCertificateDto certificateDto){
        return new GiftCertificate(certificateDto.getId(),
                certificateDto.getName(),
                certificateDto.getDescription(),
                certificateDto.getPrice(),
                certificateDto.getDuration(),
                certificateDto.getCreateDate(),
                certificateDto.getLastUpdateDate(),
                certificateDto.getTags()
                        .stream()
                        .map(EntityWrapper::toTag)
                        .collect(Collectors.toList())
        );
    }

    public static User toUser(UserDto userDto){
        return new User(userDto.getId(), userDto.getName());
    }

    public static Order toOrder(OrderDto orderDto){
        return new Order(orderDto.getId(), toUser(orderDto.getUser()), toGiftCertificate(orderDto.getGiftCertificate()), orderDto.getOrderDate(), orderDto.getCost());
    }
}
