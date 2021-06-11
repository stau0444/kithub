package com.toyproject.kithub;

import com.toyproject.kithub.controller.form.FoodForm;
import com.toyproject.kithub.domain.*;
import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.FoodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    //스프링이  다 올라오고 호출해 준다.
    //스프링 라이프 사이클이 있어서 @PostConstruct 메서드에
    //트랜젝션을 붙히고 하면 작동이 잘 안된다 .
    //아래처럼 따로 메서드를 뽑아서 호출 시켜줘야한다.
    @PostConstruct
    public void init(){
        initService.dbInit();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit(){
            Member member = createMember("member1", "서울", "성수로", "1111");
            em.persist(member);

            Food food1 = Food.createFood(new FoodForm("라면키트", 3000, 300, "chef1", FoodType.JAPANESE));
            Food food2 = Food.createFood(new FoodForm("떡볶이키트", 3000, 300, "chef2", FoodType.KOREAN));

            em.persist(food1);
            em.persist(food2);

            OrderItem orderItem1 = OrderItem.createOrderItem(food1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(food2, 20000, 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.creatOrder(member, delivery, orderItem1, orderItem2);
            em .persist(order);
        }

        public void dbInit2(){

            Member member = createMember("member2", "양주", "호국로", "11112");
            em.persist(member);

            Food food1 = Food.createFood(new FoodForm("우동키트", 30000, 300, "chef1", FoodType.JAPANESE));
            Food food2 = Food.createFood(new FoodForm("김치키트", 40000, 300, "chef2", FoodType.KOREAN));

            em.persist(food1);
            em.persist(food2);

            OrderItem orderItem1 = OrderItem.createOrderItem(food1, 30000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(food2, 40000, 4);

            Delivery delivery = createDelivery(member);

            Order order = Order.creatOrder(member, delivery, orderItem1, orderItem2);
            em .persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setDeliveryStatus(DeliveryStatus.READY);
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Member createMember(String name, String city, String street ,String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}

