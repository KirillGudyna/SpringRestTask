package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderDto extends RepresentationModel<OrderDto> {

    private Long id;
    private UserDto user;
    private GiftCertificateDto giftCertificate;
    private String orderDate;
    private BigDecimal cost;

    public OrderDto(Long id, UserDto user, GiftCertificateDto giftCertificate, String orderDate, BigDecimal cost) {
        this.id = id;
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public GiftCertificateDto getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificateDto giftCertificateDTO) {
        this.giftCertificate = giftCertificateDTO;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        OrderDto orderDTO = (OrderDto) o;

        if (!Objects.equals(id, orderDTO.id)) {
            return false;
        }
        if (!Objects.equals(user, orderDTO.user)) {
            return false;
        }
        if (!Objects.equals(giftCertificate, orderDTO.giftCertificate)) {
            return false;
        }
        return Objects.equals(cost, orderDTO.cost);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
