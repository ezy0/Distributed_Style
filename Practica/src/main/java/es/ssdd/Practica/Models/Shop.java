package es.ssdd.Practica.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    private Long id;
    private String name;
    private String image;
    private String direction;
    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Supplier> suppliers = new ArrayList<>();

    public ArrayList<Supplier> getSuppliers () {
        return suppliers;
    }

    public void setSuppliers (ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Shop(String name, String image, String direction) {
        this.name = name;
        this.image = image;
        this.direction = direction;
    }

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

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public ArrayList<Product> getProducts () {
        return products;
    }

    public void setProducts (ArrayList<Product> products) {
        this.products = products;
    }
}
