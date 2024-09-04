package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.User;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testInserts() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Item item = Item.builder()
                    .name("상품"+i)
                    .price(BigDecimal.valueOf(100+i))
                    // 아래 이미지는 현재 몽고디비에 있는 임의의 이미지를 재사용함
                    .itemRepImageId("66d7cc29e1fc5d445d8920b7")
                    .description("상품 설명 " + i)
                    .build();

            itemRepository.save(item);
        });
    }
}
