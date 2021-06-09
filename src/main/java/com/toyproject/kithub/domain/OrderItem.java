package com.toyproject.kithub.domain;

import com.toyproject.kithub.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //생성 메서드
    public static OrderItem createOrderItem(Item item,int orderPrice ,int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //주문을 했으니 item의 재고 수량을 줄인다.
        item.minusStock(count);
        return orderItem;
    }

    //주문 취소시 재고수량을 원복시킴
    public void cancel(){
        getItem().addStock(count);
    }

    //조회 로직

    //주문 상품 전체 가격 조회
   public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }

}
