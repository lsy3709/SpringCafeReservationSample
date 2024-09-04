package com.busanit501lsy.springcafereservationsample.controller;

import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ItemImage;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ProfileImage;
import com.busanit501lsy.springcafereservationsample.service.ItemService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/items")
@Log4j2
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String getAllItems(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
//        List<Item> items = itemService.getAllItems();
//        model.addAttribute("items", items);
//        return "items/items"; // Assuming there is a Thymeleaf template at src/main/resources/templates/items/list.html
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Item> itemPage = itemService.getAllItemsWithPage(pageable);

        model.addAttribute("items", itemPage.getContent());
        model.addAttribute("pageNumber", itemPage.getNumber());
        model.addAttribute("totalPages", itemPage.getTotalPages());
        model.addAttribute("pageSize", itemPage.getSize());

        return "items/items"; // Thymeleaf 템플릿
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

    @GetMapping("/new")
    public String createItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/create-item"; // Form for creating a new item
    }

    @PostMapping("/new")
    public String createItem(@ModelAttribute Item item , @RequestParam(value = "itemRepImage", required = false) MultipartFile itemRepImage,
                             @RequestParam(value = "itemAdd1", required = false) MultipartFile itemAdd1,
                             @RequestParam(value = "itemAdd2", required = false) MultipartFile itemAdd2,
                             @RequestParam(value = "itemAdd3", required = false) MultipartFile itemAdd3,
                             @RequestParam(value = "itemAdd4", required = false) MultipartFile itemAdd4) {
//        itemService.createItem(item);
//        return "redirect:/items";
        try {
            Item savedItem = itemService.createItem(item); // Item 저장을 한 번만 실행
            // 대표 상품 이미지 처리
            if (itemRepImage != null && !itemRepImage.isEmpty()) {
                log.info("lsy item img1 : " + savedItem.getId() + itemRepImage.getOriginalFilename() );
                itemService.saveItemImage(savedItem.getId(), itemRepImage,0);
            }
            if (itemAdd1 != null && !itemAdd1.isEmpty()) {
                log.info("lsy item img2 : " + savedItem.getId() + itemAdd1.getOriginalFilename() );
                itemService.saveItemImage(savedItem.getId(), itemAdd1,1);
            }
            if (itemAdd2 != null && !itemAdd2.isEmpty()) {
                log.info("lsy item img3 : " + savedItem.getId() + itemAdd2.getOriginalFilename() );
                itemService.saveItemImage(savedItem.getId(), itemAdd2,2);
            }
            if (itemAdd3 != null && !itemAdd3.isEmpty()) {
                log.info("lsy item img4 : " + savedItem.getId() + itemAdd3.getOriginalFilename() );
                itemService.saveItemImage(savedItem.getId(), itemAdd3,3);
            }
            if (itemAdd4 != null && !itemAdd4.isEmpty()) {
                log.info("lsy item img5 : " + savedItem.getId() + itemAdd4.getOriginalFilename() );
                itemService.saveItemImage(savedItem.getId(), itemAdd4,4);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save user profile image", e);
        }
        return "redirect:/users";
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

    // 아이템 이미지 불러오기
    @GetMapping("/{id}/itemImage")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        log.info("lsy users image 확인 ");

        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent() && item.get().getItemRepImageId() != null) {
            ItemImage itemImage = itemService.getItemImage(item.get().getItemRepImageId());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(itemImage.getContentType()))
                    .body(itemImage.getData());
        }
        return ResponseEntity.notFound().build();
    }
}
