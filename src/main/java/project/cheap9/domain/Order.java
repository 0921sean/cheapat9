package project.cheap9.domain;

import lombok.Getter;
import lombok.Setter;
import project.cheap9.exception.NotEnoughStockException;

import javax.persistence.*;
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
    private String number;

    private String zipcode;
    private String dongho;

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

    //==조회 로직==//
//    /**
//     * 주문상품 전체 가격 조회
//     */
//    public int getTotalPrice() {
//        return getOrderPrice() * getCount();
//    }

}
