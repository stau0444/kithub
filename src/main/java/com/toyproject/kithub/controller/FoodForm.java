package com.toyproject.kithub.controller;

import com.toyproject.kithub.domain.item.FoodType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FoodForm {

    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String chef;
    private FoodType foodType;

}
