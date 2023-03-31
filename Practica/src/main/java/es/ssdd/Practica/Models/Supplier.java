package es.ssdd.Practica.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Supplier {
    private Long id;
    private String name;
    private String description;
    private ArrayList<Shop> shops = new ArrayList<>();

    public Supplier(String name, String description) {
        this.name = name;
        this.description = description;
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
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public ArrayList<Shop> getShops() {
        return shops;
    }
    public void setShops(ArrayList<Shop> shops) {
        this.shops = shops;
    }
    public void addShop(Shop shop){
        this.shops.add(shop);
    }
    public void removeShop(Shop shop){
        this.shops.remove(shop);
    }
}
