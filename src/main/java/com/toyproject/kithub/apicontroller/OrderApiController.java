package com.toyproject.kithub.apicontroller;

import com.toyproject.kithub.domain.Address;
import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.domain.OrderItem;
import com.toyproject.kithub.domain.Status;
import com.toyproject.kithub.repository.OrderRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.ClosedSelectorException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;


    @GetMapping("/api/v1/orders")
    public List<Order> orderV1(){
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            //오더 - 멤버 초기화
            order.getMember().getName();
            //오더 - 딜리버리 초기화
            order.getDelivery().getAddress();
            //오더 - 오더아이템 초기화
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(orderItem -> {orderItem.getItem().getName();});
        }

        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2(){
        long st = System.currentTimeMillis();
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderList = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        long et = System.currentTimeMillis();
        System.out.println((et - st)+" ms");
        return orderList;
    }


    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){
        //속도 측정할 메소드

        long st = System.currentTimeMillis();

        List<Order> orders = orderRepository.finAllWithItem();
        List<OrderDto> orderList = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        long et = System.currentTimeMillis();
        System.out.println((et - st)+" ms");
        return orderList;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> orderV3_page(@RequestParam(value = "offset" ,defaultValue = "0") int offset,
                                       @RequestParam(value = "limit" ,defaultValue = "100") int limit){

        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        List<OrderDto> orderList = orders.stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());

        return orderList;
    }



    @Data
    static class OrderDto{

        private Long id;
        private String name;
        private LocalDateTime orderDate;
        private Status orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order o) {
            this.id = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getMember().getAddress();
            this.orderItems = o.getOrderItems().stream().map(oi ->new OrderItemDto(oi)).collect(Collectors.toList());

        }

        @Data
        static class OrderItemDto{
            private String itemName;
            private int orderPrice;
            private int count;

            public OrderItemDto(OrderItem orderItem) {
                this.itemName = orderItem.getItem().getName();
                this.orderPrice = orderItem.getOrderPrice();
                this.count = orderItem.getCount();
            }
        }

    }
}
