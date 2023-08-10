package project.cheap9.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Item;
import project.cheap9.service.ItemService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 상품 정보 저장하기
     */
    @PostMapping("/api/admin/items")
    public CreateItemResponse saveItem(@RequestBody @Valid CreateItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setOriginalPrice(request.getOriginalPrice());
        item.setPrice(request.getPrice());
        item.setDiscountRate((request.getOriginalPrice() - request.getPrice()) * 100 / request.getOriginalPrice());
        item.setStockQuantity(request.getStockQuantity());

        Long id = itemService.saveItem(item);
        return new CreateItemResponse(id);
    }

    /**
     * 개별 상품 조회
     */
    @GetMapping("/api/items/{id}")
    public ItemDto getOneItem(
            @PathVariable("id") Long id) {
        Item item = itemService.findOne(id);
        ItemDto result = new ItemDto(item);
        return result;
    }

    /**
     * 전체 상품 조회
     */
    @GetMapping("/api/items")
    public List<ItemDto> items() {
        List<Item> items = itemService.findItems();
        List<ItemDto> result = items.stream()
                .map(i -> new ItemDto(i))
                .collect(Collectors.toList());
        return result;
    }

//   + 필요한 재료들
    /**
     * 입력값
     */
    @Data
    static class CreateItemRequest {
        private String name;
        private int originalPrice;
        private int price;
        private int stockQuantity;
    }

    /**
     * 출력값
     */
    @Data
    static class CreateItemResponse {
        private Long id;

        public CreateItemResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class ItemDto {
        private Long itemId;
        private String name;
        private int originalPrice;
        private int price;
        private int discountRate;
        private int stockQuantity;

        public ItemDto(Item item) {
            itemId = item.getId();
            name = item.getName();
            originalPrice = item.getOriginalPrice();
            price = item.getPrice();
            discountRate = item.getDiscountRate();
            stockQuantity = item.getStockQuantity();
        }
    }

}
