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
import java.util.ArrayList;
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

    @GetMapping("/suppliers/{id}")
    public String getSupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);

        model.addAttribute("shops", supplier.getShops());
        model.addAttribute("id",supplier.getId());
        model.addAttribute("name",supplier.getName());
        model.addAttribute("description",supplier.getDescription());

        return "viewSupplier";
    }

    @GetMapping("/suppliers/newSupplier")
    public String newSupplier(Model model){
        model.addAttribute("shops", this.shopService.getShops());
        return "newSupplier";
    }

    @GetMapping("/suppliers/redirectNewSupplier")
    public String newSupplier(HttpServletRequest request, @RequestParam String name, @RequestParam String description) {
        ArrayList<Shop> shops = new ArrayList<>();
        String[] selectedValues = request.getParameterValues("checkbox");
        if (selectedValues != null) {
            for (String value : selectedValues) {
                shops.add(this.shopService.getShop(Long.parseLong(value)));
            }
        }
        Supplier supplier = new Supplier(name, description, shops);
        this.supplierService.createSupplier(supplier);
        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.shopService.getShop(Long.parseLong(value)).getSuppliers().add(supplier);
            }
        }
        return "redirect:/suppliers";

    }

    @GetMapping("/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable long id){
        Supplier supplier = this.supplierService.deleteSupplier(id);
        if (supplier == null) {
            return "redirect:/error";
        }
        if (supplier.getShops() != null)
            for (Shop shop : supplier.getShops())
                for(Shop shop2 : this.shopService.getShops())
                    if (Objects.equals(shop.getId(), shop2.getId()))
                        this.shopService.getShop(shop.getId()).getSuppliers().remove(supplier);
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

    @GetMapping("/suppliers/redirectModifySupplier")
    public String redirectModifySupplier(@RequestParam("idSupplier") long idSupplier,@RequestParam("name") String name, @RequestParam("description") String description) {
        this.supplierService.modifySupplier(idSupplier,new Supplier(name, description, null));
        return "redirect:/suppliers/" + idSupplier;
    }

    @GetMapping("/suppliers/{id}/addShopsToSupplier")
    public String addShopsToSupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        Collection<Shop> shops = this.shopService.getShops();

       // Collection<Shop> shopsSupplier = supplier.getShops();
       // Collection<Shop> shops2 = this.shopService.getShops();
        //shops.retainAll(shopsSupplier); operacion de interseccion, tampoco funciona

/*
        for(Shop shop: shops){ //No va, muestra todos
            if(!supplier.getShops().contains(shop))
                shops2.add(shop);
        }

        for(Shop shop: shops){ //No va
            if(supplier.getShops().contains(shop))
                shops.remove(shop);
        }
         */

        model.addAttribute("shops",shops); //Solo quiero mostrar las tiendas que no tienen ese supplier
        model.addAttribute("idSupplier",supplier.getId());
        //model.addAttribute("name",supplier.getName());

        return "addShopsToSupplier";
    }

    @GetMapping("/suppliers/{id}/redirectAddShopsToSupplier")
    public String redirectAddShopsToSupplier(HttpServletRequest request,@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        String[] selectedValues = request.getParameterValues("checkbox");

        if (selectedValues != null) { //Se añade a las tiendas el supplier
            for (String value : selectedValues) {
                this.shopService.getShop(Long.parseLong(value)).getSuppliers().add(supplier);
            }
        }

        if (selectedValues != null) { //Se añade al supplier las tiendas
            for (String value : selectedValues) {
                this.supplierService.getSupplier(supplier.getId()).getShops().add(shopService.getShop(Long.parseLong(value)));
            }
        }

        return "redirect:/suppliers/"+id;
    }

    @GetMapping("/suppliers/{id}/removeShopsToSupplier")
    public String removeShopsToSupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);

        model.addAttribute("shops",supplier.getShops()); //Solo quiero mostrar las tiendas de ese suplier, no todas
        model.addAttribute("idSupplier",supplier.getId());
        //model.addAttribute("name",supplier.getName());

        return "removeShopsToSupplier";
    }

    @GetMapping("/suppliers/{id}/redirectRemoveShopsToSupplier")
    public String redirectRemoveShopsToSupplier(HttpServletRequest request,@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        String[] selectedValues = request.getParameterValues("checkbox");

        if (selectedValues != null) { //Se eliminan a las tiendas el supplier
            for (String value : selectedValues) {
                this.shopService.getShop(Long.parseLong(value)).getSuppliers().remove(supplier);
            }
        }

        if (selectedValues != null) { //Se añade al supplier las tiendas
            for (String value : selectedValues) {
                this.supplierService.getSupplier(supplier.getId()).getShops().remove(shopService.getShop(Long.parseLong(value)));
            }
        }

        return "redirect:/suppliers/"+id;
    }




}
