package com.toyproject.kithub.service;

import com.toyproject.kithub.controller.FoodForm;
import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.Item;
import com.toyproject.kithub.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void  updateItem(Long itemId, FoodForm updateForm){
        Food searchedItem = (Food) itemRepository.findOne(itemId);
        searchedItem.updateItem(updateForm);
    }

    public List<Item> findItem(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }
}
