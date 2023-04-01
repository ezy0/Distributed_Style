package es.ssdd.Practica.Controller;


import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Models.Supplier;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import es.ssdd.Practica.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Objects;

@Controller
public class SupplierController {
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;

    @GetMapping("/suppliers")
    public String getSuppliers(Model model){
        model.addAttribute("suppliers", this.supplierService.getSuppliers());
        return "showSuppliers";
    }

    @GetMapping("/supplier/{id}")
    public String getSupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);

        model.addAttribute("id",supplier.getId());
        model.addAttribute("name",supplier.getName());
        model.addAttribute("description",supplier.getDescription());

        return "viewSupplier";
    }

    @GetMapping("suppliers/newSupplier")
    public String newSupplier(Model model, HttpServletRequest request, @RequestParam String name, @RequestParam String description, @RequestParam long id){
        // seguir
        model.addAttribute("products", this.productService.getProducts());
        Supplier supplier = new Supplier(name,description);
        supplierService.createSupplier(supplier);
        return "showSuppliers";
    }

    @DeleteMapping("/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable long id){
        Supplier supplier = this.supplierService.deleteSupplier(id);
        if (supplier == null) {
            return "redirect:/error";
        }
        Collection<Shop> shopsList = this.shopService.getShops();
        for(Shop shop : shopsList){ //Nos recorremos todas las tiendas
            for(Supplier supplierAux : shop.getSuppliers()){ //Nos recorremos cada uno de los suppliers de esa tienda
                if (Objects.equals(supplier.getId(), supplierAux.getId())){ //Si el supplier que se quiere eliminar reparte a una tienda...
                    shop.getSuppliers().remove(supplierAux); //El supplier se elimina de la lista de suppliers de la tienda
                }
            }
        }

        return "showSuppliers";
    }

    @GetMapping("/suppliers/modifySupplier/{id}")
    public String modifySupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);

        model.addAttribute("id",supplier.getId());
        model.addAttribute("name",supplier.getName());
        model.addAttribute("description",supplier.getDescription());

        return "modifySupplier";
    }

    @PutMapping("/suppliers/redirectModifySupplier")
    public String redirectModifySupplier(@RequestParam("idSupplier") long idSupplier,@RequestParam("name") String name, @RequestParam("description") String description) {
        this.supplierService.modifySupplier(idSupplier,new Supplier(name, description));
        return "redirect:/supplier/" + idSupplier;
    }


}
