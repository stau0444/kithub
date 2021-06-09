package com.toyproject.kithub.service;

import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.FoodType;
import com.toyproject.kithub.domain.item.Item;
import com.toyproject.kithub.domain.item.Lecture;
import com.toyproject.kithub.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired ItemService itemService;

    @Test
    void Save_Item_And_Update_Stock_Quantity() throws  Exception{
        //given
        Food food = new Food();
        food.setStockQuantity(100);
        Long save = itemRepository.save(food);
        //when
        Item savedItem = itemRepository.findOne(save);
        savedItem.addStock(100);
        savedItem.minusStock(50);

        //then
        assertEquals(savedItem.getStockQuantity(),150);
    }

    @Test
    void findItemsTest() throws  Exception{
        //given
        Food food = new Food();
        food.setName("김밥");
        food.setFoodType(FoodType.KOREAN);
        Lecture lecture = new Lecture();
        lecture.setInstructor("Instructor1");
        lecture.setName("왕초보 주식 따라하기");

        itemRepository.save(food);
        itemRepository.save(lecture);

        //when
        List<Item> all = itemRepository.findAll();
        //then
        assertEquals(2, all.size());
    }



}