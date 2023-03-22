package es.ssdd.Practica.Models;

import java.util.HashMap;

public class Supplier {
    private Long id;
    private String name;
    private HashMap<Long, Product> products;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Long, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Long, Product> products) {
        this.products = products;
    }
}
