package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class ShopRESTController {
    @Autowired
    ShopService shopService;
    @GetMapping("/shops")
    public ResponseEntity<Collection<Shop>> getProducts() {
        return new ResponseEntity<>(this.shopService.getShops(), HttpStatus.OK);
    }

    @GetMapping("/shops/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @PostMapping("/shops/newShop")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop){
        return new ResponseEntity<>(this.shopService.createShop(shop), HttpStatus.OK);
    }

    @DeleteMapping("/shops/delete/{id}")
    public ResponseEntity<Shop> deleteShop(@PathVariable long id){
        Shop shop = this.shopService.deleteShop(id);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }
}
