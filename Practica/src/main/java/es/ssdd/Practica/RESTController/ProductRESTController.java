package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ProductRESTController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Collection<Product>> getProducts() {
        return new ResponseEntity<>(this.productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id){
        Product product = this.productService.getProduct(id);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/products/newProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(this.productService.createProduct(product), HttpStatus.OK);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        Product product = this.productService.deleteProduct(id);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
