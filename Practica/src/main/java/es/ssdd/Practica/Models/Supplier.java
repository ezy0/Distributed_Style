package es.ssdd.Practica.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Supplier {
    private Long id;
    private String name;
    private ArrayList<Product> products;

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
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
