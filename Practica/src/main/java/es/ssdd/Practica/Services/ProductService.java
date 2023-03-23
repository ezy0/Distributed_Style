package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private HashMap<Long, Product> products = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public Product createProduct(Product product){
        product.setId(lastId.incrementAndGet());
        products.put(lastId.get(), product);
        return product;
    }

    public Collection<Product> getProducts(){
        return this.products.values().stream().toList();
    }

    public ProductService(){
        createProduct(new Product("White Alien Hoodie", 80.00f, null, "assets/img/alienSudaderaSin.png"));
        createProduct(new Product("Purple Alien Hoodie", 80.00f, null, "assets/img/alienSudadera2Sin.png"));
        createProduct(new Product("Black Alien Hoodie", 80.00f, null, "assets/img/alienSudadera3Sin.png"));
    }


}
