package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.User;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ItemImage;
import com.busanit501lsy.springcafereservationsample.entity.mongoEntity.ProfileImage;
import com.busanit501lsy.springcafereservationsample.repository.ItemRepository;
import com.busanit501lsy.springcafereservationsample.repository.mongoRepository.ItemImageRepository;
import com.busanit501lsy.springcafereservationsample.repository.mongoRepository.ProfileImageRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // 프로필 이미지, 몽고디비 연결
    @Autowired
    ItemImageRepository itemImageRepository;

    public Page<Item> getAllItemsWithPage(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

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
    public void saveItemImage(Long itemId, MultipartFile file, int check) throws IOException {
        // Get the user
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("lsy item img imgservice : " + item.getId() + file.getOriginalFilename() );
        // Create and save the profile image
        ItemImage itemImage = new ItemImage(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
        ItemImage savedImage = itemImageRepository.save(itemImage);

        // Link the profile image to the user
        log.info("lsy item img imgservice : " + savedImage.getId());
        // 분기 필요.
        item.setItemRepImageId(savedImage.getId());
        // check 값에 따라 이미지 저장 로직 분기
        switch (check) {
            case 0:
                // 대표 이미지 저장
                item.setItemRepImageId(savedImage.getId());
                break;
            case 1:
                // 추가 상품 1 이미지 저장
                item.setItemAdd1ImageId(savedImage.getId());
                break;
            case 2:
                // 추가 상품 2 이미지 저장
                item.setItemAdd2ImageId(savedImage.getId());
                break;
            case 3:
                // 추가 상품 3 이미지 저장
                item.setItemAdd3ImageId(savedImage.getId());
                break;
            case 4:
                // 추가 상품 4 이미지 저장
                item.setItemAdd4ImageId(savedImage.getId());
                break;
            default:
                throw new IllegalArgumentException("Invalid check value: " + check);
        }
        itemRepository.save(item);
    }
    // 이미지 가져오기
    public ItemImage getItemImage(String imageId) {
        return itemImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }


} //end