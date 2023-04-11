package es.ssdd.Practica.RESTController;

import com.fasterxml.jackson.annotation.JsonView;
import es.ssdd.Practica.Models.Composition;
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

    interface ShopDetails extends Shop.Basic, Shop.Suppliers, Supplier.Basic, Product.Basic, Composition.Basic{}

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
        if (shop.getDirection() == null || shop.getName() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.shopService.createShop(shop), HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @DeleteMapping("/shops/{id}/deleteShop")
    public ResponseEntity<Shop> deleteShop(@PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        if (shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (shop.getProducts().size() > 0)
            for (Product product : shop.getProducts()) //All products in the shop are removed
                for (Product product2 : this.productService.getProducts())
                    if (Objects.equals(product.getId(), product2.getId())){
                        if (product2.getComposition() != null && this.compositionService.getComposition(product2.getComposition().getId()) != null)
                            this.compositionService.deleteComposition(product2.getComposition().getId()); //Also eliminate the composition of each product, in case the product has a composition
                        this.productService.deleteProduct(product2.getId());
                    }
        if (shop.getSuppliers().size() > 0)
            for (Supplier supplier : shop.getSuppliers())
                for (Supplier supplier2 : this.supplierService.getSuppliers()) //The shop is deleted from the list of suppliers' shops
                    if (Objects.equals(supplier.getId(), supplier2.getId()))
                        this.supplierService.getSupplier(supplier.getId()).getShops().remove(shop);

        return new ResponseEntity<>(this.shopService.deleteShop(id), HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @PutMapping("/shops/{id}/modifyShop")
    public ResponseEntity<Shop> modifyShop(@PathVariable long id, @RequestBody Shop modifiedShop){
        if (modifiedShop.getDirection() == null || modifiedShop.getName() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        this.shopService.getShop(id).getSuppliers().add(supplier); //Add the supplier to the shop suppliers list
        this.supplierService.getSupplier(idSupplier).getShops().add(shop); //Add the shop to the supplier shops list
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @JsonView(ShopDetails.class)
    @PutMapping("/shops/{id}/removeSupplier")
    public ResponseEntity<Shop> removeSupplier(@PathVariable long id, @RequestParam long idSupplier){
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        Shop shop = this.shopService.getShop(id);
        if (supplier == null || shop == null || !shop.getSuppliers().contains(supplier))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.supplierService.getSupplier(idSupplier).getShops().remove(shop); //Remove the shop to the supplier shops list
        this.shopService.getShop(id).getSuppliers().remove(supplier); //Remove the supplier to the shop suppliers list
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }
}