package com.toyproject.kithub.controller;

import com.toyproject.kithub.controller.form.FoodForm;
import com.toyproject.kithub.domain.item.Food;
import com.toyproject.kithub.domain.item.Item;
import com.toyproject.kithub.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/items/new")
    public String createForm(Model  model){

        model.addAttribute("form", new FoodForm());
        return "items/createItemForm";

    }

    @PostMapping("/items/new")
    public String create(FoodForm form){

        Food food = Food.createFood(form);
        itemService.saveItem(food);
        return "redirect:/items";

    }

    @GetMapping("/items")
    public String getItemList(Model model){

        List<Item> item = itemService.findItem();
        model.addAttribute("items",item);
        return "items/items";

    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable Long itemId,Model model){

        Food searchedItem = (Food)itemService.findOne(itemId);
        FoodForm foodForm = modelMapper.map(searchedItem,FoodForm.class);
        model.addAttribute("form",foodForm);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    @Transactional
    public  RedirectView updateItem(@PathVariable Long itemId,FoodForm updateForm){


        itemService.updateItem(itemId,updateForm);

        return new RedirectView("/items",true);
    }
}
