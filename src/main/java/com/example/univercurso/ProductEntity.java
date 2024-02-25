package com.example.univercurso;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProductEntity {
    private String name;
    private int price;
    private int available;
    private Button addProduct;

    public ProductEntity(String name, int price, int available, Button addProduct) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.addProduct = addProduct;
    }

    public ProductEntity(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
