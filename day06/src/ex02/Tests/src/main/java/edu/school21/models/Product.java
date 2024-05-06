package edu.school21.models;

import java.util.Objects;

public class Product {
    private Long identifier;
    private String name;
    private int price;

    public Product(Long identifier, String name, int price) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(identifier, product.identifier) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "\nid=" + identifier +
                "\nname=" + name +
                "\nprice=" + price +
                "\n}";
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
