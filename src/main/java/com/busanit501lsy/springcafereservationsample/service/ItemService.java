package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ItemImage;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ProfileImage;
import com.busanit501lsy.springcafereservationsample.repository.ItemRepository;
import com.busanit501lsy.springcafereservationsample.repository.mongoRepository.ItemImageRepository;
import com.busanit501lsy.springcafereservationsample.repository.mongoRepository.ProfileImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // 프로필 이미지, 몽고디비 연결
    @Autowired
    ItemImageRepository itemImageRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item itemDetails) {
        return itemRepository.findById(id).map(item -> {
            item.setName(itemDetails.getName());
            item.setPrice(itemDetails.getPrice());
            item.setDescription(itemDetails.getDescription());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    //프로필 이미지 업로드, 레스트 형식
    public void saveItemImage(Long itemId, MultipartFile file) throws IOException {
        // Get the user
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and save the profile image
        ItemImage itemImage = new ItemImage(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
        ItemImage savedImage = itemImageRepository.save(itemImage);

        // Link the profile image to the user
        item.setItemRepImageId(savedImage.getId());
        itemRepository.save(item);
    }
}