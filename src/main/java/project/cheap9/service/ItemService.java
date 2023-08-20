package project.cheap9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.cheap9.domain.Item;
import project.cheap9.repository.ItemRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 상품 검색
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void update(Long id, String name, int originalPrice, int price, int stockQuantity, String startDate, String endDate) {
        Item item = itemRepository.findOne(id);
        item.setName(name);
        item.setOriginalPrice(originalPrice);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        LocalDateTime start = LocalDateTime.parse(startDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        item.setStartDate(start);
        LocalDateTime end = LocalDateTime.parse(endDate,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        item.setEndDate(end);
    }

}
