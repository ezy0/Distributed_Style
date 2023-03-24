package es.ssdd.Practica.Models;

public class Composition {
    private Long id;
    private String content;
    private Product product;

    public Composition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Review [id=" + id  + content + ", product=" + product + "]";
    }
}
