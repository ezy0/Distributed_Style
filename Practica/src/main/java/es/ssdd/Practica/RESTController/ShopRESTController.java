package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Models.Supplier;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import es.ssdd.Practica.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ShopRESTController {
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    CompositionService compositionService;

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

    @DeleteMapping("/shops/{id}/deleteShop")
    public ResponseEntity<Shop> deleteShop(@PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (shop.getProducts().size() > 0)
            for (Product product : shop.getProducts())
                for (Product product2 : this.productService.getProducts())
                    if (Objects.equals(product.getId(), product2.getId())){
                        if (this.compositionService.getComposition(product2.getComposition().getId()) != null)
                            this.compositionService.deleteComposition(product2.getComposition().getId());
                        this.productService.deleteProduct(product2.getId());
                    }

        if (shop.getProducts().size() > 0)
            for (Supplier supplier : shop.getSuppliers())
                for (Supplier supplier2 : this.supplierService.getSuppliers())
                    if (Objects.equals(supplier.getId(), supplier2.getId()))
                        this.supplierService.removeShop(supplier.getId(), this.shopService.getShop(id));

        return new ResponseEntity<>(this.shopService.deleteShop(id), HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/modifyShop")
    public ResponseEntity<Shop> modifyShop(@PathVariable long id, @RequestBody Shop modifiedShop){
        Shop shop = this.shopService.modifyShop(id, modifiedShop);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/addSupplier")
    public ResponseEntity<Shop> addSupplier(@PathVariable long id, @RequestParam long idSupplier){
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        Shop shop = this.shopService.addSupplier(id, supplier);
        if (shop == null || supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.supplierService.addShop(id, shop);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/removeSupplier")
    public ResponseEntity<Shop> removeSupplier(@PathVariable long id, @RequestParam long idSupplier){
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        Shop shop = this.shopService.removeSupplier(id, supplier);
        if (shop == null || supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.supplierService.removeShop(id, shop);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }
}
