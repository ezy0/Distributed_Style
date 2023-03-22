package es.ssdd.Practica.Models;

import java.util.HashMap;
import java.util.Objects;

public class Product {
    private Long id;
    private String name;
    private float prize;
    private Review review;
    private HashMap<Long, Product> products;
    private HashMap<Long, Supplier> suppliers;

   public Product(Long id,String name,float prize,Review review){
       this.id=id;
       this.name=name;
       this.prize=prize;
       this.review=review;
   }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }


    public float getPrize() {
        return this.prize;
    }

    public Review getReview() {
        return this.review;
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

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.prize, prize) == 0 && Objects.equals(name, product.name) && Objects.equals(review, product.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, prize, review);
    }
}
