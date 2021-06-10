package com.toyproject.kithub.domain;


import com.toyproject.kithub.exception.CannotCancelException;
import lombok.*;

import javax.persistence.*;
import java.lang.annotation.Target;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(value = EnumType.STRING)
    private Status status;


    //연관관계 편의 메서드는 핵심적으로 비지니스를 컨트롤 하는쪽에 넣어주는게 좋다.

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }


    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //생성 메서드
    public static Order creatOrder(Member member ,Delivery delivery, OrderItem... orderItems){
        //주문 생성
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        //주문 상품 추가
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        //주문 정보
        order.setStatus(Status.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //주문 취소
    public void cancel(){
        if(delivery.getDeliveryStatus() == DeliveryStatus.CAMP){
            throw new CannotCancelException("배송이 시작된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(Status.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //조회 로직

    //전체 주문 가격 조회
    public int getTotalPrice(){
        int orderPrice = 0 ;
        for (OrderItem orderItem : orderItems) {
            orderPrice = orderPrice+ orderItem.getTotalPrice();
        }
        return orderPrice;
    }


}
