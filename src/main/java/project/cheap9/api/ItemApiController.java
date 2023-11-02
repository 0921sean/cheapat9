package project.cheap9.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Item;
import project.cheap9.dto.item.request.CreateItemRequest;
import project.cheap9.dto.item.request.UpdateItemRequest;
import project.cheap9.dto.item.response.CreateItemResponse;
import project.cheap9.dto.item.response.ItemDto;
import project.cheap9.dto.item.response.UpdateItemResponse;
import project.cheap9.service.ItemService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime startDate = LocalDateTime.parse(request.getStartDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        item.setStartDate(startDate);
        LocalDateTime endDate = LocalDateTime.parse(request.getEndDate(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        item.setEndDate(endDate);

        Long id = itemService.saveItem(item);
        return new CreateItemResponse(id);
    }

    /**
     * 상품 정보 수정하기
     */
    @PutMapping("/api/admin/items/{id}")
    public UpdateItemResponse updateItem(@PathVariable("id") Long id,
                                         @RequestBody @Valid UpdateItemRequest request) {
        itemService.update(id, request.getName(), request.getOriginalPrice(), request.getPrice(),
                request.getStockQuantity(), request.getStartDate(), request.getEndDate());
        Item item = itemService.findOne(id);
        return new UpdateItemResponse(item.getId());
    }

    /**
     * 개별 상품 조회
     */
    @GetMapping("/api/items/{id}")
    public ItemDto getOneItem(@PathVariable("id") Long id) {
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

}
