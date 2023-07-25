package project.cheap9.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int count;

    private String name;
    private String number;

    private String zipcode;
    private String dongho;

    private String pw;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    //==생성 메서드==//
//    public static Order createOrder(Item item, int orderPrice, int count) {
//        Order order = new Order();
//        order.setItem(item);
//        order.setOrderPrice(orderPrice);
//        order.setCount(count);
//        order.setStatus(OrderStatus.WAITING);
//        order.setOrderDate(LocalDateTime.now());
//
//        item.removeStock(count);
//        return order;
//    }

//    public static Order createOrder(Item item, int count, String name, String number,
//                                    String zipcode, String dongho, String pw) {
//        Order order = new Order();
//        order.setItem(item);
//        order.setCount(count);
//        order.setName(name);
//        order.setNumber(number);
//        order.setZipcode(zipcode);
//        order.setDongho(dongho);
//        order.setPw(pw);
//        order.setStatus(OrderStatus.WAITING);
//        order.setOrderDate(LocalDateTime.now());
//
//        item.removeStock(count);
//        return order;
//    }

    //==조회 로직==//
    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

}
