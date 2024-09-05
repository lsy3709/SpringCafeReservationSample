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

        int startPage = Math.max(0, itemPage.getNumber() - 5);
        int endPage = Math.min(itemPage.getTotalPages() - 1, itemPage.getNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
        return "redirect:/items";
    }


    @GetMapping("/{id}/edit")
    public String updateItemForm(@PathVariable Long id, Model model) {
        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {
            model.addAttribute("item", item.get());
            return "items/update-item"; // Form for editing the item
        } else {
            return "error/404";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute Item itemDetails,
                             @RequestParam(value = "itemRepImage", required = false) MultipartFile itemRepImage,
                             @RequestParam(value = "itemAdd1", required = false) MultipartFile itemAdd1,
                             @RequestParam(value = "itemAdd2", required = false) MultipartFile itemAdd2,
                             @RequestParam(value = "itemAdd3", required = false) MultipartFile itemAdd3,
                             @RequestParam(value = "itemAdd4", required = false) MultipartFile itemAdd4) {
        try {
            // 1. Item 저장 (기본 정보 업데이트)
            Item savedItem = itemService.updateItem(id, itemDetails);

            // 2. 대표 이미지 업데이트
            if (itemRepImage != null && !itemRepImage.isEmpty()) {
                log.info("lsy 수정 대표 이미지 업데이트: " + savedItem.getId() + ", 파일명: " + itemRepImage.getOriginalFilename());

                // 기존 대표 이미지 삭제
                Optional<Item> loadItem = itemService.getItemById(savedItem.getId());
                loadItem.ifPresent(existingItem -> {
                    itemService.deleteItemImage(existingItem, 0); // 이미지 타입 0: 대표 이미지 삭제
                });

                // 새로운 대표 이미지 저장
                itemService.saveItemImage(savedItem.getId(), itemRepImage, 0);
            }

            // 3. 추가 이미지 1 업데이트
            if (itemAdd1 != null && !itemAdd1.isEmpty()) {
                log.info("lsy 수정 추가 이미지 1 업데이트: " + savedItem.getId() + ", 파일명: " + itemAdd1.getOriginalFilename());

                // 기존 추가 이미지 1 삭제
                Optional<Item> loadItem = itemService.getItemById(savedItem.getId());
                loadItem.ifPresent(existingItem -> {
                    itemService.deleteItemImage(existingItem, 1); // 이미지 타입 1: 추가 이미지 1 삭제
                });

                // 새로운 추가 이미지 1 저장
                itemService.saveItemImage(savedItem.getId(), itemAdd1, 1);
            }

            // 4. 추가 이미지 2 업데이트
            if (itemAdd2 != null && !itemAdd2.isEmpty()) {
                log.info("lsy 수정 추가 이미지 2 업데이트: " + savedItem.getId() + ", 파일명: " + itemAdd2.getOriginalFilename());

                // 기존 추가 이미지 2 삭제
                Optional<Item> loadItem = itemService.getItemById(savedItem.getId());
                loadItem.ifPresent(existingItem -> {
                    itemService.deleteItemImage(existingItem, 2); // 이미지 타입 2: 추가 이미지 2 삭제
                });

                // 새로운 추가 이미지 2 저장
                itemService.saveItemImage(savedItem.getId(), itemAdd2, 2);
            }

            // 5. 추가 이미지 3 업데이트
            if (itemAdd3 != null && !itemAdd3.isEmpty()) {
                log.info("lsy 수정 추가 이미지 3 업데이트: " + savedItem.getId() + ", 파일명: " + itemAdd3.getOriginalFilename());

                // 기존 추가 이미지 3 삭제
                Optional<Item> loadItem = itemService.getItemById(savedItem.getId());
                loadItem.ifPresent(existingItem -> {
                    itemService.deleteItemImage(existingItem, 3); // 이미지 타입 3: 추가 이미지 3 삭제
                });

                // 새로운 추가 이미지 3 저장
                itemService.saveItemImage(savedItem.getId(), itemAdd3, 3);
            }

            // 6. 추가 이미지 4 업데이트
            if (itemAdd4 != null && !itemAdd4.isEmpty()) {
                log.info("lsy 수정 수정 추가 이미지 4 업데이트: " + savedItem.getId() + ", 파일명: " + itemAdd4.getOriginalFilename());

                // 기존 추가 이미지 4 삭제
                Optional<Item> loadItem = itemService.getItemById(savedItem.getId());
                loadItem.ifPresent(existingItem -> {
                    itemService.deleteItemImage(existingItem, 4); // 이미지 타입 4: 추가 이미지 4 삭제
                });

                // 새로운 추가 이미지 4 저장
                itemService.saveItemImage(savedItem.getId(), itemAdd4, 4);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to update item images", e);
        }
        return "redirect:/items";
    }

    @GetMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/items";
    }

    // 아이템 이미지 불러오기
    @GetMapping("/{id}/itemImage")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id,
                                                  @RequestParam(value = "type", required = false) String type) {
        log.info("lsy users image 확인 ");

        Optional<Item> item = itemService.getItemById(id);

        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // 널 가능성 있는 type 처리
        Optional<ItemImage> itemImage = Optional.empty();

        if (type == null || "rep".equals(type)) {
            itemImage = Optional.ofNullable(itemService.getItemImage(item.get().getItemRepImageId()));
        } else if ("add1".equals(type)) {
            itemImage = Optional.ofNullable(itemService.getItemImage(item.get().getItemAdd1ImageId()));
        } else if ("add2".equals(type)) {
            itemImage = Optional.ofNullable(itemService.getItemImage(item.get().getItemAdd2ImageId()));
        } else if ("add3".equals(type)) {
            itemImage = Optional.ofNullable(itemService.getItemImage(item.get().getItemAdd3ImageId()));
        } else if ("add4".equals(type)) {
            itemImage = Optional.ofNullable(itemService.getItemImage(item.get().getItemAdd4ImageId()));
        }

        if (item.isPresent() && itemImage != null) {

//            ItemImage itemImage = itemService.getItemImage(item.get().getItemRepImageId());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(itemImage.get().getContentType()))
                    .body(itemImage.get().getData());
        }



        return ResponseEntity.notFound().build();
    }
}
