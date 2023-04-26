package es.ssdd.Practica.Models;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Supplier {
    public interface Shops{}
    public interface Basic{}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Basic.class)
    private Long id;

    @JsonView(Basic.class)
    private String name;
    @JsonView(Basic.class)
    private String description;

    @ManyToMany(mappedBy = "suppliers")
    @JsonView(Shops.class)
    private List<Shop> shops = new ArrayList<>();

    public Supplier(){}

    public Supplier(String name, String description, List<Shop> shops) {
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
    public List<Shop> getShops() {
        return shops;
    }
    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }
}