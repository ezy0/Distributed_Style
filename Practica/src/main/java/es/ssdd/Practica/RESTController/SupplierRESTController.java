package es.ssdd.Practica.RESTController;

import com.fasterxml.jackson.annotation.JsonView;
import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.SupplierService;
import es.ssdd.Practica.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import es.ssdd.Practica.Models.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class SupplierRESTController {

    @Autowired
    ProductService productService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    ShopService shopService;

    interface SupplierDetails extends Supplier.Basic, Supplier.Shops, Shop.Basic, Product.Basic, Composition.Basic{}

    @JsonView(SupplierDetails.class)
    @GetMapping("/suppliers")
    public ResponseEntity<Collection<Supplier>> getSuppliers(){
        return new ResponseEntity<>(this.supplierService.getSuppliers(), HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PostMapping("/suppliers/newSupplier")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier){
        if (supplier.getShops() == null || supplier.getDescription() == null || supplier.getName() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(this.supplierService.saveSupplier(supplier), HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @DeleteMapping("suppliers/{idSupplier}/deleteSupplier")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable long idSupplier){
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (supplier.getShops().size()>0){ //If the supplier is in more than one store... It is deleted from all stores
            for(Shop shop : supplier.getShops() ){
                for(Shop shopAux: this.shopService.getShops()) { //Go through all the suppliers of each one of the stores
                    if (Objects.equals(shop.getId(), shopAux.getId())){ //If the ids match, remove
                        this.shopService.getShop(shop.getId()).getSuppliers().remove(supplier);
                        this.shopService.saveShop(this.shopService.getShop(shop.getId()));
                    }
                }
            }
        }
        return new ResponseEntity<>(this.supplierService.deleteSupplier(idSupplier),HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PutMapping("suppliers/{idSupplier}/modifySupplier")
    public ResponseEntity<Supplier> modifySupplier(@PathVariable long idSupplier,@RequestBody Supplier modifiedSupplier){
        if (modifiedSupplier.getDescription() == null || modifiedSupplier.getName() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Supplier supplier =this.supplierService.modifySupplier(idSupplier,modifiedSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PutMapping("/suppliers/{idSupplier}/addShop")
    public ResponseEntity<Supplier> addShop(@PathVariable long idSupplier,@RequestParam long idShop){
        Shop shop = this.shopService.getShop(idShop);
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        if (shop == null || supplier == null || supplier.getShops().contains(shop))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.shopService.getShop(idShop).getSuppliers().add(supplier);
        this.shopService.saveShop(shop);
        this.supplierService.getSupplier(idSupplier).getShops().add(shop);
        return new ResponseEntity<>(this.supplierService.saveSupplier(supplier),HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PutMapping("suppliers/{idSupplier}/removeShop")
    public ResponseEntity<Supplier> removeShop(@PathVariable long idSupplier,@RequestParam long idShop){
        Shop shop = this.shopService.getShop(idShop);
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        if (shop == null || supplier == null || !supplier.getShops().contains(shop))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.shopService.getShop(idShop).getSuppliers().remove(supplier);
        this.shopService.saveShop(shop);
        this.supplierService.getSupplier(idSupplier).getShops().remove(shop);
        this.supplierService.saveSupplier(supplier);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }
}
