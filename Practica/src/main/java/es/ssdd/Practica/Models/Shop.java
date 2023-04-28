package es.ssdd.Practica.Models;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shop {
    public interface Suppliers{}
    public interface Basic{}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Basic.class)
    private Long id;

    @JsonView(Basic.class)
    private String name;
    @JsonView(Basic.class)
    private String image;
    @JsonView(Basic.class)
    private String direction;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "shop_product",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @JsonView(Basic.class)
    private List<Product> products = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shop_supplier",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    @JsonView(Suppliers.class)
    private List<Supplier> suppliers = new ArrayList<>();

    public List<Supplier> getSuppliers () {
        return suppliers;
    }

    public void setSuppliers (List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Shop () {
    }

    public Shop(String name, String image, String direction) {
        this.name = name;
        if (image == null || image.length() == 0)
            this.image = "/assets/img/new.jpg"; //Set default image
        else
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

    public List<Product> getProducts () {
        return products;
    }

    public void setProducts (List<Product> products) {
        this.products = products;
    }
}
