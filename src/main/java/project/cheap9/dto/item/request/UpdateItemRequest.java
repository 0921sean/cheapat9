package project.cheap9.dto.item.request;

import lombok.Data;

@Data
public class UpdateItemRequest {
    private String name;
    private int originalPrice;
    private int price;
    private int stockQuantity;
    private String startDate;
    private String endDate;
}
