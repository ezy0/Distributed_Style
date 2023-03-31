package es.ssdd.Practica.RESTController;

import com.fasterxml.jackson.annotation.JsonView;
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

    interface ShopDetails extends Shop.Basic, Shop.Suppliers, Supplier.Basic{}

    @JsonView(ShopDetails.class)
    @GetMapping("/shops")
    public ResponseEntity<Collection<Shop>> getProducts() {
        return new ResponseEntity<>(this.shopService.getShops(), HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @GetMapping("/shops/{id}")
    public ResponseEntity<Shop> getShop(@PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @PostMapping("/shops/newShop")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop){
        return new ResponseEntity<>(this.shopService.createShop(shop), HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
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
        if (shop.getSuppliers().size() > 0)
            for (Supplier supplier : shop.getSuppliers())
                for (Supplier supplier2 : this.supplierService.getSuppliers())
                    if (Objects.equals(supplier.getId(), supplier2.getId()))
                        this.supplierService.getSupplier(supplier.getId()).getShops().remove(this.shopService.getShop(id));

        return new ResponseEntity<>(this.shopService.deleteShop(id), HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @PutMapping("/shops/{id}/modifyShop")
    public ResponseEntity<Shop> modifyShop(@PathVariable long id, @RequestBody Shop modifiedShop){
        Shop shop = this.shopService.modifyShop(id, modifiedShop);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @PutMapping("/shops/{id}/addSupplier")
    public ResponseEntity<Shop> addSupplier(@PathVariable long id, @RequestParam long idSupplier){
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        Shop shop = this.shopService.getShop(id);
        if (supplier == null || shop == null || shop.getSuppliers().contains(supplier))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.shopService.getShop(id).getSuppliers().add(supplier);
        this.supplierService.getSupplier(idSupplier).getShops().add(shop);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @PutMapping("/shops/{id}/removeSupplier")
    public ResponseEntity<Shop> removeSupplier(@PathVariable long id, @RequestParam long idSupplier){

        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        Shop shop = this.shopService.getShop(id);
        if (supplier == null || shop == null || !shop.getSuppliers().contains(supplier))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.shopService.getShop(id).getSuppliers().remove(supplier);
        this.supplierService.getSupplier(id).getShops().remove(shop);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }
}
