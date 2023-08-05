package project.cheap9.domain;

import lombok.Getter;
import lombok.Setter;
import project.cheap9.exception.NotEnoughStockException;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int beforePrice;
    private int price;
    private int discountRate;
    private int stockQuantity;



    //==비즈니스 로직==//
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
