package project.cheap9.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemForm {

    public Long id;
    public String name;
    public int beforePrice;
    public int price;
    private int stockQuantity;

}
