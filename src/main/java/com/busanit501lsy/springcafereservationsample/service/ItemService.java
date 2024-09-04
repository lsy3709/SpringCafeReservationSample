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
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // 상품 대표 이미지 삭제
        if(item.getItemRepImageId() != null && !item.getItemRepImageId().isEmpty()) {
            deleteItemImage(item,0);
        }
        // 상품 대표 이미지 삭제
        if(item.getItemAdd1ImageId() != null && !item.getItemAdd1ImageId().isEmpty()) {
            deleteItemImage(item,1);
        }
        // 상품 대표 이미지 삭제
        if(item.getItemAdd2ImageId() != null && !item.getItemAdd2ImageId().isEmpty()) {
            deleteItemImage(item,2);
        }
        // 상품 대표 이미지 삭제
        if(item.getItemAdd3ImageId() != null && !item.getItemAdd3ImageId().isEmpty()) {
            deleteItemImage(item,3);
        }
        // 상품 대표 이미지 삭제
        if(item.getItemAdd4ImageId() != null && !item.getItemAdd4ImageId().isEmpty()) {
            deleteItemImage(item,4);
        }
        itemRepository.deleteById(id);
    }

    //프로필 이미지 업로드, 레스트 형식
    public void saveItemImage(Long itemId, MultipartFile file, int check) throws IOException {
        // Get the user
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("lsy 수정 item img imgservice : " + item.getId() + file.getOriginalFilename() );
        // Create and save the profile image
        ItemImage itemImage = new ItemImage(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
        ItemImage savedImage = itemImageRepository.save(itemImage);

        // Link the profile image to the user
        log.info("lsy 수정 item img imgservice : " + savedImage.getId());
        // 분기 필요.
//        item.setItemRepImageId(savedImage.getId());
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

    // 프로필 이미지만 삭제
    public void deleteItemImage(Item item, int imageType) {
        String imageId = null;

        // 이미지 타입에 따라 삭제할 이미지 ID 결정
        switch (imageType) {
            case 0: // 대표 이미지
                imageId = item.getItemRepImageId();
                break;
            case 1: // 추가 이미지 1
                imageId = item.getItemAdd1ImageId();
                break;
            case 2: // 추가 이미지 2
                imageId = item.getItemAdd2ImageId();
                break;
            case 3: // 추가 이미지 3
                imageId = item.getItemAdd3ImageId();
                break;
            case 4: // 추가 이미지 4
                imageId = item.getItemAdd4ImageId();
                break;
            default:
                log.warn("잘못된 이미지 타입입니다: " + imageType);
                return;
        }

        log.info("lsy 수정 삭제할 이미지 ID: " + imageId);

        // 이미지 ID가 존재하면 삭제 작업 수행
        if (imageId != null) {
            // MongoDB에서 이미지 삭제
            itemImageRepository.deleteById(imageId);

            // 해당 이미지 ID 필드를 null로 설정
            switch (imageType) {
                case 0:
                    item.setItemRepImageId(null);
                    break;
                case 1:
                    item.setItemAdd1ImageId(null);
                    break;
                case 2:
                    item.setItemAdd2ImageId(null);
                    break;
                case 3:
                    item.setItemAdd3ImageId(null);
                    break;
                case 4:
                    item.setItemAdd4ImageId(null);
                    break;
            }

            // 업데이트된 Item 정보 저장
            itemRepository.save(item);
            log.info("lsy 수정 이미지 삭제 및 Item 업데이트 완료 : " + imageType);
        } else {
            log.warn("삭제할 이미지가 없습니다. 이미지 타입: " + imageType);
        }
    }


} //end