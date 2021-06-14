package com.toyproject.kithub.repository.order.query;

import com.toyproject.kithub.domain.Address;
import com.toyproject.kithub.domain.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderQueryDto {

    private Long id;
    private String name;
    private LocalDateTime orderDateTime;
    private Status status;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long id, String name, LocalDateTime orderDateTime, Status status, Address address) {
        this.id = id;
        this.name = name;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.address = address;
    }

    public OrderQueryDto(Long id, String name, LocalDateTime orderDateTime, Status status, Address address, List<OrderItemQueryDto> orderItems) {
        this.id = id;
        this.name = name;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.address = address;
        this.orderItems = orderItems;
    }
}
