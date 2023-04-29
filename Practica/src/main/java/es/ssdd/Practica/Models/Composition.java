package es.ssdd.Practica.Models;

import com.fasterxml.jackson.annotation.JsonView;
import org.owasp.html.Sanitizers;
import javax.persistence.*;

@Entity
public class Composition {

    public interface Basic{}
    public interface Details{}

    @JsonView(Details.class)
    private Long productId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Details.class)
    private Long id;
    @JsonView(Basic.class)
    private String content;

    public Composition(){}

    public Composition(String content) {
        this.content = Sanitizers.FORMATTING.sanitize(content);
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
        this.content = Sanitizers.FORMATTING.sanitize(content);
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
