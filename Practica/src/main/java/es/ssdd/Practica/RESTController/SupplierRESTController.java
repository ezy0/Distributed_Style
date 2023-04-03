package es.ssdd.Practica.RESTController;

import com.fasterxml.jackson.annotation.JsonView;
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

import java.util.ArrayList;
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

    interface SupplierDetails extends Supplier.Basic, Supplier.Shops, Shop.Basic, Product.Basic{}

    @JsonView(SupplierDetails.class)
    @GetMapping("/suppliers")
    public ResponseEntity<Collection<Supplier>> getSuppliers(){
        return new ResponseEntity<>(this.supplierService.getSuppliers(), HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSuppliersShop(@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PostMapping("/suppliers/newSupplier")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier){
        return new ResponseEntity<>(this.supplierService.createSupplier(supplier), HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @DeleteMapping("suppliers/{idSupplier}/deleteSupplier")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable long idSupplier){
        Supplier supplier = this.supplierService.deleteSupplier(idSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (supplier.getShops().size()>0){ //If the supplier is in more than one store... It is deleted from all stores
            for(Shop shop : supplier.getShops() ){ //Recorro todas las tiendas en las que está ese supplier
                for(Shop shopAux: this.shopService.getShops()){ //Go through all the suppliers of each one of the stores
                    if (Objects.equals(shop.getId(), shopAux.getId())) //If the ids match, remove
                        this.shopService.getShop(shop.getId()).getSuppliers().remove(supplier);
                }
            }
        }
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PutMapping("suppliers/{idSupplier}/modifySupplier")
    public ResponseEntity<Supplier> modifySupplier(@PathVariable long idSupplier,@RequestBody Supplier modifiedSupplier){
        Supplier supplier =this.supplierService.modifySupplier(idSupplier,modifiedSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PutMapping("suppliers/{idSupplier}/addShop")
    public ResponseEntity<Supplier> addShop(@PathVariable long idSupplier,@RequestParam long idShop){
        Shop shop = this.shopService.getShop(idShop);
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        if (shop == null || supplier == null || supplier.getShops().contains(shop))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.supplierService.getSupplier(idSupplier).getShops().add(shop);
        this.shopService.getShop(idShop).getSuppliers().add(supplier);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @JsonView(SupplierDetails.class)
    @PutMapping("suppliers/{idSupplier}/removeShop")
    public ResponseEntity<Supplier> removeShop(@PathVariable long idSupplier,@RequestParam long idShop){
        Shop shop = this.shopService.getShop(idShop);
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        if (shop == null || supplier == null || !supplier.getShops().contains(shop))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.supplierService.getSupplier(idSupplier).getShops().remove(shop);
        this.shopService.getShop(idShop).getSuppliers().remove(supplier);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }
}
