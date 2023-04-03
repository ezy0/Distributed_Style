package es.ssdd.Practica.Models;

import java.util.ArrayList;
import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private String description;
    private float prize;
    private String image;
    private Long shopId;
    private Composition composition;

   public Product(String name, String description, float prize, Composition composition, String image, Long shopId){
       this.name=name;
       this.description = description;
       this.prize=prize;
       this.composition = composition;
       if (image.length() == 0)
           this.image = "/assets/img/sudadera.jpg";
       else
           this.image=image;
       this.shopId = shopId;
   }

   public Long getShopId () {
       return shopId;
   }
   public void setShopId (Long shopId) {
       this.shopId = shopId;
   }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDescription(){
       return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public float getPrize() {
        return this.prize;
    }
    public Composition getComposition() {
        return this.composition;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrize(float prize) {
        this.prize = prize;
    }
    public void setComposition(Composition composition) {
        this.composition = composition;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.prize, prize) == 0 && Objects.equals(name, product.name) && Objects.equals(composition, product.composition);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, prize, composition);
    }
}
