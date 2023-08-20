package project.cheap9.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import project.cheap9.exception.NotEnoughStockException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int originalPrice;
    private int price;
    private int discountRate;
    private int stockQuantity;

    /**
     * 수정 부분
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;


    //==비즈니스 로직==//
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("Error: Not enough stock");
        }
        this.stockQuantity = restStock;
    }

}
