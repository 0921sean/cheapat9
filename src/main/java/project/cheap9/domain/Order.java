package project.cheap9.domain;

import lombok.Getter;
import lombok.Setter;
import project.cheap9.exception.NotEnoughStockException;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;
    private int orderPrice;

    private String name;

    // @Pattern(regexp = "^\\d{10,11}$", message = "Phone number should be 10 or 11 digits")
    private String number;

    private String zipcode;
    private String dongho;

    // @Pattern(regexp = "^\\d{4}$", message = "Password should be exactly 4 digits")
    private String pw;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    //==생성 메서드==//
    public static void setBase(Item item, Order order) {
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderPrice(item.getPrice() * order.getCount());

        item.removeStock(order.count);
    }

//    initDB에 필요한 함수 -> 추후 삭제!
    public static Order createOrder(Item item, int orderPrice, int count,
                                    String name, String number, String zipcode, String dongho, String pw) {
        Order order = new Order();
        order.setItem(item);
        order.setOrderPrice(orderPrice);
        order.setCount(count);
        order.setName(name);
        order.setNumber(number);
        order.setZipcode(zipcode);
        order.setDongho(dongho);
        order.setPw(pw);
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==조회 로직==//
//    /**
//     * 주문상품 전체 가격 조회
//     */
//    public int getTotalPrice() {
//        return getOrderPrice() * getCount();
//    }

}
