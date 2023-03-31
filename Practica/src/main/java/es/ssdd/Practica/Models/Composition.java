package es.ssdd.Practica.Models;

public class Composition {
    private Long productId;
    private Long id;
    private String content;

    public Composition(){}

    public Composition(String content) {

        this.content = content;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Composition [productId=" + productId  + content + "]";
    }
}
