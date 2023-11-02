package project.cheap9.dto.order.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long itemId;
    private int count;
    private String name;
    private String number;
    private String zipcode;
    private String address;
    private String dongho;
    private String pw;
}
