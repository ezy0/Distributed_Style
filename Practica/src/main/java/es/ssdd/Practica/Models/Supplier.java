package es.ssdd.Practica.Models;

import com.fasterxml.jackson.annotation.JsonView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Supplier {
    public interface Shops{}
    public interface Basic{}
    @JsonView(Basic.class)
    private Long id;
    @JsonView(Basic.class)
    private String name;
    @JsonView(Basic.class)
    private String description;
    @JsonView(Shops.class)
    private ArrayList<Shop> shops = new ArrayList<>();

    public Supplier(String name, String description, ArrayList<Shop> shops) {
        this.name = name;
        this.description = description;
        this.shops = shops;
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
}
