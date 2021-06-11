package com.toyproject.kithub.controller.form;

import com.toyproject.kithub.domain.item.FoodType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FoodForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String chef;
    private FoodType foodType;


    public FoodForm(String name, int price, int stockQuantity, String chef, FoodType foodType) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.chef = chef;
        this.foodType = foodType;
    }
}
