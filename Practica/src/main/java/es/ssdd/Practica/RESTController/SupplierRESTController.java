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
    public ResponseEntity<Collection<Supplier>> getSuppliersShop(@PathVariable long idShop){
        Shop shop = shopService.getShop(idShop);
        return new ResponseEntity<>(shop.getSuppliers(), HttpStatus.OK);
    }


    @PostMapping("/suppliers/newSupplier")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier){
        return new ResponseEntity<>(this.supplierService.createSupplier(supplier), HttpStatus.OK);
    }

    @DeleteMapping("suppliers/deleteSupplier/{idSupplier}")
    public ResponseEntity<Supplier> deleteSupplier(@PathVariable long idSupplier){
        Supplier supplier = this.supplierService.getSupplier(idSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (supplier.getShops().size()>0){ //If the supplier is in more than one store... It is deleted from all stores
            for(Shop shop : supplier.getShops() ){ //Recorro todas las tiendas en las que está ese supplier
                for(Supplier supplierAux: shop.getSuppliers()){ //Go through all the suppliers of each one of the stores
                    if (Objects.equals(supplier.getId(), supplierAux.getId())) //If the ids match, remove
                        this.supplierService.deleteSupplier(supplierAux.getId());
                }
            }
        }
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }

    @PutMapping("suppliers/modify/{idSupplier}")
    public ResponseEntity<Supplier> modifySupplier(@PathVariable long idSupplier,@RequestBody Supplier modifiedSupplier){
        Supplier supplier =this.supplierService.modifySupplier(idSupplier,modifiedSupplier);
        if (supplier == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(supplier,HttpStatus.OK);
    }


}
