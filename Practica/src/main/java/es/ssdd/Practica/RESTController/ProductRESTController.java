package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;

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
    @Autowired
    ShopService shopService;
    @Autowired
    CompositionService compositionService;

    @GetMapping("/products")
    public ResponseEntity<Collection<Product>> getAllProducts() {
        return new ResponseEntity<>(this.productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/shops/{id}/products")
    public ResponseEntity<Collection<Product>> getProducts(@PathVariable long id) {
        Collection<Product> products= this.productService.getProducts();
        for (Product product : products)
            if (product.getShopId() != id)
                products.remove(product);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/shops/{id}/{idP}")
    public ResponseEntity<Product> getProduct(@PathVariable long idP, @PathVariable long id){
        Product product = this.productService.getProduct(idP);
        if (product == null || product.getShopId() != id)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/shops/{id}/newProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable long id){
        this.productService.createProduct(product, id);
        this.shopService.getShop(id).getProducts().add(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/shops/{id}/{idP}/deleteProduct")
    public ResponseEntity<Product> deleteProduct(@PathVariable long idP, @PathVariable long id){
        Product product = this.productService.deleteProduct(idP);
        this.shopService.getShop(product.getShopId()).getProducts().remove(product);
        if (product.getComposition() != null)
            this.compositionService.deleteComposition(product.getComposition().getId());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/{idP}/modifyProduct")
    public ResponseEntity<Product> modifyProduct (@RequestBody Product product, @PathVariable long id, @PathVariable long idP){
        if (this.productService.getProduct(idP) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(this.productService.modifyProduct(idP, id, product), HttpStatus.OK);
    }
}