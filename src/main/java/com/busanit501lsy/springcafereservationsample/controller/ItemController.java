package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String getAllItems(Model model) {
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "items/list"; // Assuming there is a Thymeleaf template at src/main/resources/templates/items/list.html
    }

    @GetMapping("/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "items/detail"; // Assuming there is a Thymeleaf template at src/main/resources/templates/items/detail.html
        } else {
            return "error/404"; // Assuming a custom error page for not found
        }
    }

    @GetMapping("/create")
    public String createItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/create"; // Form for creating a new item
    }

    @PostMapping("/create")
    public String createItem(@ModelAttribute Item item) {
        itemService.createItem(item);
        return "redirect:/items";
    }

    @GetMapping("/edit/{id}")
    public String updateItemForm(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "items/edit"; // Form for editing the item
        } else {
            return "error/404";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute Item itemDetails) {
        itemService.updateItem(id, itemDetails);
        return "redirect:/items";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/items";
    }
}
