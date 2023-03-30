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

}
