package com.toyproject.kithub.service;

import com.toyproject.kithub.domain.*;
import com.toyproject.kithub.domain.item.Item;
import com.toyproject.kithub.repository.ItemRepository;
import com.toyproject.kithub.repository.MemberRepository;
import com.toyproject.kithub.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;


    //주문
    @Transactional
    public Long order(Long memberid , Long itemId , int count){
        //멤버와 아이템 엔티티 조회
        Member member = memberRepository.findOne(memberid);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        //주문 생성
        Order order = Order.creatOrder(member, delivery, orderItem);

        //주문 저장
        //cascade 옵션떄문에 orderItem 과 delivery가 자동으로 persist된다.
        //참조하는 곳이 1곳이면 cascade를 사용하는 것이 좋지만 여러곳에서 참조하고 있다면
        //사용하면 안된다.
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        //order엔티티안에 비지니스로직이 들어있어서 훨씬 깔끔하다.
        order.cancel();
        //JPA 강점 더티 체킹을 통해 변경 포인트를 알아서 update query를 날려준다
    }

    //검색

    //public List<Order> searchOrders(OrderSearch orderSearch){
     //   return orderRepository.findAll(orderSearch);
    //}


}
