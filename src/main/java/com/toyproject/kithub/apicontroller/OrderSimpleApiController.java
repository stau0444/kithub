package com.toyproject.kithub.apicontroller;

import com.toyproject.kithub.domain.Address;
import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.domain.Status;
import com.toyproject.kithub.repository.OrderRepository;
import com.toyproject.kithub.repository.OrderSearch;
import com.toyproject.kithub.repository.SimpleOrderQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAll();
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        //3개의 테이블을 조인하고 있다.
        return orderRepository.findAll().stream()
                .map(order -> new SimpleOrderDto(order))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){
        //페치 조인으로 가져온다.
        return orderRepository.findAllWithMemberDelivery()
                    .stream()
                    .map(order ->new SimpleOrderDto(order))
                    .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> findOrderDto(){
        //페치 조인으로 가져온다.
        return orderRepository.findOrderDto();
    }

    @Data
    @AllArgsConstructor
    static class SimpleOrderDto{
        private Long id;
        private String name;
        private LocalDateTime orderDateTime;
        private Status status;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.id = order.getId();
            this.name = order.getMember().getName();
            this.orderDateTime = order.getOrderDate();
            this.status = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
    
}
