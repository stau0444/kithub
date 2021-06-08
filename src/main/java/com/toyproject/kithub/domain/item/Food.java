package com.toyproject.kithub.domain.item;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
