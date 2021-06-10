package com.toyproject.kithub.service;

import com.toyproject.kithub.domain.*;
import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.FoodType;
import com.toyproject.kithub.exception.NotEnoughStockException;
import com.toyproject.kithub.repository.ItemRepository;
import com.toyproject.kithub.repository.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    /*
    도메인 패턴 개발 테스트는
    - 도메인에 정의된 비지니스 메서드들을 단위테스트하고
    - 아래처럼 종합적으로 돌아가는지에 대해 테스트를 따로해보는 것이 좋다.
    */

    //주문 테스트
    @Test
    void orderTest(){

        //given
        Member member = getMember("member1", new Address("양주", "고읍남로", "12333"));

        Food food = getFood("나카무라상", 100, 3000, "우동 키트", FoodType.JAPANESE);

        //when
        Long orderId = orderService.order(member.getId(), food.getId(), 10);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        //주문시 재고수량 감소
        assertEquals(Status.ORDER, getOrder.getStatus(),"주문시 주문의 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(),"주문상품 종류의 수가 정확해야함");
        assertEquals(30000,getOrder.getTotalPrice(),"주문 총가격은 주문상품 * 주문상품 수량");
        assertEquals(90, food.getStockQuantity(), "주문한 상품의 재고가 줄어야 한다");

    }
    @Test
    void 상품주문_재고수량초과() throws  Exception{
        //given
        Member member = getMember("ugo", new Address("양주", "고읍남로", "12333"));
        Food food = getFood("나카무라", 3, 300, "우동", FoodType.JAPANESE);

        //when

        //then
        assertThrows(NotEnoughStockException.class,()->{
            orderService.order(member.getId(), food.getId(), 11);
        });
    }


    //주문 취소
    @Test
    void cancel() throws  Exception{
        //given
        Member member = getMember("member1", new Address("뉴욕시", "뉴욕로", "4044"));
        Food food = getFood("안드레아", 100, 2000, "스테이크", FoodType.WESTERN);
        //when
        Long orderId = orderService.order(member.getId(), food.getId(), 10);
        orderService.cancelOrder(orderId);
        Order findOrder = orderRepository.findOne(orderId);
        //then
        assertEquals(100,food.getStockQuantity(),"취소시 상품의 재고가 원복되야 한다.");
        assertEquals(Status.CANCEL,findOrder.getStatus(),"주문 취소시 주문상태는 CANCEL이다.");
    }


    private Food getFood(String chef, int stockQuantity, int price, String name, FoodType foodType) {
        Food food = new Food();
        food.setFoodType(foodType);
        food.setChef(chef);
        food.setStockQuantity(stockQuantity);
        food.setPrice(price);
        food.setName(name);
        em.persist(food);
        return food;
    }

    private Member getMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }
}