package es.ssdd.Practica.Models;

public class Composition {
    private Long id;
    private String content;

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

    @Override
    public String toString() {
        return "Composition [id=" + id  + content + "]";
    }
}
