package project.cheap9.dto.order.response;

import lombok.Data;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private Long orderId;
    private Item item;
    private int orderPrice;
    private int count;
    private String name;
    private String number;
    private String zipcode;
    private String address;
    private String dongho;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    public OrderDto(Order order) {
        orderId = order.getId();
        item = order.getItem();
        orderPrice = order.getOrderPrice();
        count = order.getCount();
        name = order.getName();
        number = order.getNumber();
        zipcode = order.getZipcode();
        address = order.getAddress();
        dongho = order.getDongho();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
    }

}
