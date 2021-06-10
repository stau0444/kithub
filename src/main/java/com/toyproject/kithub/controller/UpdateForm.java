package com.toyproject.kithub.controller;

import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.FoodType;
import com.toyproject.kithub.domain.item.Item;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class UpdateForm {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String chef;
    private FoodType foodType;

}
