package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Composition;
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
        for (Product product : products) //We select the correct products
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
        if (product.getDescription() == null || product.getName() == null || product.getPrize() < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (product.getImage() == null)
            product.setImage("/assets/img/new.jpg"); //Set default image
        this.productService.saveProduct(product, id);
        this.shopService.getShop(id).getProducts().add(product);
        this.productService.saveProduct(product, id);
        this.shopService.saveShop(this.shopService.getShop(id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/shops/{id}/{idP}/deleteProduct")
    public ResponseEntity<Product> deleteProduct(@PathVariable long idP, @PathVariable long id){
        Product product = this.productService.getProduct(idP);
        if (product == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Composition composition = product.getComposition();
        if (composition != null) { //If product has composition, also delete the composition
            product.setComposition(null);
            compositionService.deleteComposition(composition.getId());
        }
        this.shopService.getShop(id).getProducts().remove(this.productService.saveProduct(product));
        this.productService.deleteProduct(idP);
        this.shopService.saveShop(this.shopService.getShop(id));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/{idP}/modifyProduct")
    public ResponseEntity<Product> modifyProduct (@RequestBody Product product, @PathVariable long id, @PathVariable long idP){
        if (product.getDescription() == null || product.getName() == null || product.getPrize() < 0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (this.productService.getProduct(idP) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (product.getImage() == null)
            product.setImage("/assets/img/new.jpg"); //Set default image
        return new ResponseEntity<>(this.productService.modifyProduct(idP, id, product), HttpStatus.OK);
    }
}