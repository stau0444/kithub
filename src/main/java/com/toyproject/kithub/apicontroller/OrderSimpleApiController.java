package com.toyproject.kithub.apicontroller;

import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.repository.OrderRepository;
import com.toyproject.kithub.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAll();
        return all;
    }
    
}
