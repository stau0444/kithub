package com.toyproject.kithub.domain.item;

import com.toyproject.kithub.controller.FoodForm;
import com.toyproject.kithub.domain.Category;
import com.toyproject.kithub.exception.NotEnoughStockException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories;

    //비지니스 로직
    //도메인 주도 개발시 엔티티가 가지고 있는 필드에 대한 비지니스로직은
    //엔티티가 갖고 있는 것이 응집도가 높아진다

    //재고 증가 메서드
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    //재고 감소 메서드
    public void minusStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw  new NotEnoughStockException("재고가 주문수량보다 적습니다");
        }
        this.stockQuantity = restStock;
    }


}
