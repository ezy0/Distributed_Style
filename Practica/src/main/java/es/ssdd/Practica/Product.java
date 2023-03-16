package es.ssdd.Practica;

import java.util.ArrayList;
import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private ProductType type;
    private float prize;
    private ArrayList<Review> list;

   public Product(int id,String name,ProductType type,float prize,ArrayList<Review> list){
       this.id=id;
       this.name=name;
       this.type=type;
       this.prize=prize;
       this.list=list;
   }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public ProductType getType() {
        return this.type;
    }

    public float getPrize() {
        return this.prize;
    }

    public ArrayList<Review> getList() {
        return this.list;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public void setList(ArrayList<Review> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.prize, prize) == 0 && Objects.equals(name, product.name) && type == product.type && Objects.equals(list, product.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, prize, list);
    }
}
