package com.toyproject.kithub.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.internal.JsonFormatter;
import com.toyproject.kithub.domain.*;
import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.FoodType;
import com.toyproject.kithub.domain.item.Item;
import com.toyproject.kithub.exception.NotEnoughStockException;
import com.toyproject.kithub.repository.ItemRepository;
import com.toyproject.kithub.repository.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired EntityManager em;

    @BeforeEach
    void setOrder(TestInfo testInfo){
        if (
                testInfo.getDisplayName().equals("reduceStock")
                || testInfo.getDisplayName().equals("orderTest")
                || testInfo.getDisplayName().equals("cancelTest")
        ){
            //member 생성
            Member member = getMember();

            //delivery 생성
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());

            Food food = new Food();
            food.setName("ramen");
            food.setFoodType(FoodType.JAPANESE);
            food.setStockQuantity(100);
            food.setChef("나카모토상");
            food.setPrice(3000);
            em.persist(food);

            orderService.order(member.getId(),food.getId(),5);
        }

    }

    //주문 test
    @Test
    @DisplayName(value = "orderTest")
    void 상품주문() throws  Exception{

        //when
        List<Order> all = orderRepository.findAll();
        Order searchedOrder = all.get(0);
        //then
        assertNotNull(searchedOrder);
    }

    //상품 주문시 재고 수량 초과확인 test
    @Test
    @DisplayName(value = "reduceStock")
    void 상품주문후_재고_수량_감소확인() throws  Exception{

        //when
        List<Item> items = itemRepository.findAll();

        //then
        assertEquals(96,items.get(0).getStockQuantity());
    }

    //상품 초과시 NotEnoughStockException
    @Test
    void 상품재고수량_초과시_예외발생() throws  Exception{
        //given
        //member 생성
        Member member = getMember();

        //delivery 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        Food food = new Food();
        food.setName("ramen");
        food.setFoodType(FoodType.JAPANESE);

        //when
        food.setStockQuantity(3);
        food.setChef("나카모토상");
        food.setPrice(3000);
        em.persist(food);

        //then
        NotEnoughStockException notEnoughStockException = assertThrows(NotEnoughStockException.class, () -> {
            OrderItem orderItem = OrderItem.createOrderItem(food, food.getPrice(), 4);
            Order order = Order.creatOrder(member, delivery, orderItem);
            orderRepository.save(order);
        });

        System.out.println(notEnoughStockException.getMessage());

    }

    private Member getMember() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "시청로", "12000"));
        em.persist(member);
        return member;
    }


    //주문 취소(취소시 재고가 원복이 되는지 , 주문 상태가 바뀌는지지) test
    @Test
    @DisplayName("cancelTest")
    void 취소시_상품재고_원복확인() throws  Exception{
        //given
        List<Order> all = orderRepository.findAll();
        Order order = all.get(0);
        //when
        order.cancel();
        //then;
        List<Item> items = itemRepository.findAll();
        Item item = items.get(0);

        assertEquals(100,item.getStockQuantity());
    }
}