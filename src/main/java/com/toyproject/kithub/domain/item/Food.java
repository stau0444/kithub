package com.toyproject.kithub.domain.item;


import com.toyproject.kithub.controller.FoodForm;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("F")
public class Food  extends Item{

    private String chef;

    @Enumerated(value = EnumType.STRING)
    private FoodType foodType;

    public void updateItem(FoodForm updateForm){
        this.setName(updateForm.getName());
        this.setStockQuantity(updateForm.getStockQuantity());
        this.setPrice(updateForm.getPrice());
        this.setFoodType(updateForm.getFoodType());
        this.setChef(updateForm.getChef());
    }
    public static Food createFood(FoodForm form){
        Food food = new Food();
        food.setName(form.getName());
        food.setPrice(form.getPrice());
        food.setStockQuantity(form.getStockQuantity());
        food.setChef(form.getChef());
        food.setFoodType(form.getFoodType());
        return food;
    };

}
