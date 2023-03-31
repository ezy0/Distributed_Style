package es.ssdd.Practica.RESTController;

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


    @GetMapping("/suppliers")
    public ResponseEntity<Collection<Supplier>> getSuppliers(){
        return new ResponseEntity<>(this.supplierService.getSuppliers(), HttpStatus.OK);
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<Supplier> getSuppliersShop(@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    @PostMapping("/suppliers/newSupplier")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier){
        return new ResponseEntity<>(this.supplierService.createSupplier(supplier), HttpStatus.OK);
    }

    @DeleteMapping("suppliers/{idSupplier}/deleteSupplier")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable long idSupplier){
        Supplier supplier = this.supplierService.deleteSupplier(idSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (supplier.getShops().size()>0){ //If the supplier is in more than one store... It is deleted from all stores
            for(Shop shop : supplier.getShops() ){ //Recorro todas las tiendas en las que está ese supplier
                for(Supplier supplierAux: shop.getSuppliers()){ //Go through all the suppliers of each one of the stores
                    if (Objects.equals(supplier.getId(), supplierAux.getId())) //If the ids match, remove
                        this.shopService.removeSupplier(shop.getId(), supplierAux);
                }
            }
        }
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @PutMapping("suppliers/{idSupplier}/modifySupplier")
    public ResponseEntity<Supplier> modifySupplier(@PathVariable long idSupplier,@RequestBody Supplier modifiedSupplier){
        Supplier supplier =this.supplierService.modifySupplier(idSupplier,modifiedSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        /*for (Shop shop : this.shopService.getShops())
            for (Supplier supplier1 : shop.getSuppliers())
                if (Objects.equals(supplier1.getId(), supplier.getId())){
                    supplier1.setDescription(supplier.getDescription());
                    supplier1.setName(supplier.getName());
                }*/
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @PutMapping("suppliers/{idSupplier}/addShop")
    public ResponseEntity<Supplier> addShop(@PathVariable long idSupplier,@RequestParam long idShop){
        Shop shop = this.shopService.getShop(idShop);
        Supplier supplier =this.supplierService.addShop(idSupplier,shop);
        if (supplier == null || shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.shopService.addSupplier(idSupplier, supplier);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @PutMapping("suppliers/{idSupplier}/removeShop")
    public ResponseEntity<Supplier> removeShop(@PathVariable long idSupplier,@RequestParam long idShop){
        Shop shop = this.shopService.getShop(idShop);
        Supplier supplier =this.supplierService.removeShop(idSupplier,shop);
        if (supplier == null || shop == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.shopService.removeSupplier(idSupplier, supplier);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

}
