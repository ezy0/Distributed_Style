package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ProductRESTController {

    @Autowired
    ProductService productService;

    @GetMapping("/shops/{id}/products")
    public ResponseEntity<Collection<Product>> getProducts() {
        return new ResponseEntity<>(this.productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/shops/{id}/{idP}")
    public ResponseEntity<Product> getProduct(@PathVariable long idP){
        Product product = this.productService.getProduct(idP);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/shops/{id}/newProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable long id){
        this.productService.createProduct(product, id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/shops/{id}/{idP}/delete")
    public ResponseEntity<Product> deleteProduct(@PathVariable long idP){
        if (this.productService.getProduct(idP) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Product product = this.productService.deleteProduct(idP);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/{idP}/modifyProduct")
    public ResponseEntity<Product> modifyProduct (@RequestBody Product product, @PathVariable long id, @PathVariable long idP){
        return new ResponseEntity<>(this.productService.modifyProduct(idP, id, product), HttpStatus.OK);
    }
}
