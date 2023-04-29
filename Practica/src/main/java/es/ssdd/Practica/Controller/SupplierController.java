package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Models.Supplier;
import es.ssdd.Practica.Repositories.SupplierRepository;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import es.ssdd.Practica.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
public class SupplierController {
    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    SupplierRepository supplierRepository;

    @GetMapping("/suppliers")
    public String getSuppliers(Model model){
        model.addAttribute("suppliers", this.supplierService.getSuppliers());
        return "showSuppliers";
    }

    @GetMapping("/suppliers/")
    public String getSuppliersOrderByName(Model model, @RequestParam String sortString){

        //Convert String to Sort
        String[] sortParams = sortString.split(",");
        Sort.Direction direction = Sort.Direction.fromString(sortParams[1]);
        Sort sort = Sort.by(direction, sortParams[0]);

        List<Supplier> suppliers = new ArrayList<>();
        if (sort.toString().equals("name: ASC")) {
            suppliers = supplierRepository.findAll(Sort.by("name").ascending());
        } else if (sort.toString().equals("name: DESC")) {
            suppliers = supplierRepository.findAll(Sort.by("name").descending());
        }

        model.addAttribute("suppliers", suppliers);
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
        ArrayList<Shop> shops = new ArrayList<>(); //Auxiliary list to save the selected shops
        String[] selectedValues = request.getParameterValues("checkbox");
        if (selectedValues != null) {
            for (String value : selectedValues) {
                shops.add(this.shopService.getShop(Long.parseLong(value))); //Add the shops to the auxiliary shops
            }
        }
        Supplier supplier = new Supplier(name, description, shops); //Use the auxiliary list to create the supplier
        this.supplierService.saveSupplier(supplier);
        if (selectedValues != null) { //Add the supplier to the shop suppliers list
            for (String value : selectedValues) {
                this.shopService.getShop(Long.parseLong(value)).getSuppliers().add(supplier);
                this.shopService.saveShop(this.shopService.getShop(Long.parseLong(value)));
            }
        }
        this.supplierService.saveSupplier(supplier);
        return "redirect:/suppliers";

    }

    @GetMapping("/suppliers/delete/{id}")
    public String deleteSupplier(@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        if (supplier == null) {
            return "redirect:/error";
        }
        if (supplier.getShops() != null) {
            for (Shop shop : supplier.getShops())
                for (Shop shop2 : this.shopService.getShops())
                    if (Objects.equals(shop.getId(), shop2.getId())) {
                        this.shopService.getShop(shop.getId()).getSuppliers().remove(supplier);
                        this.shopService.saveShop(this.shopService.getShop(shop.getId()));
                    }
            //The supplier is deleted from the list of shops' suppliers
        }
        this.supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }

    @GetMapping("/suppliers/modifySupplier/{id}")
    public String modifySupplier(Model model, @PathVariable long id){ //Send the data to HTML of the supplier that you want to modify
        Supplier supplier = this.supplierService.getSupplier(id);

        model.addAttribute("id",supplier.getId());
        model.addAttribute("name",supplier.getName());
        model.addAttribute("description",supplier.getDescription());

        return "modifySupplier";
    }

    @GetMapping("/suppliers/redirectModifySupplier")
    public String redirectModifySupplier(@RequestParam("idSupplier") long idSupplier,@RequestParam("name") String name, @RequestParam("description") String description) {
        //Receive new data and modify the supplier
        this.supplierService.saveSupplier(this.supplierService.modifySupplier(idSupplier,new Supplier(name, description, null)));
        return "redirect:/suppliers/" + idSupplier;
    }

    @GetMapping("/suppliers/{id}/addShopsToSupplier")
    public String addShopsToSupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        Collection<Shop> newShops = new ArrayList<>();//Auxiliary list to save the shops to be displayed

        //You can only add shops that are not working with that supplier
        for (Shop shop : this.shopService.getShops())
            if (!supplier.getShops().contains(shop))
                newShops.add(shop);

        model.addAttribute("shops",newShops); //newShops is now the correct list
        model.addAttribute("idSupplier",supplier.getId());

        return "addShopsToSupplier";
    }

    @GetMapping("/suppliers/{id}/redirectAddShopsToSupplier")
    public String redirectAddShopsToSupplier(HttpServletRequest request,@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        String[] selectedValues = request.getParameterValues("checkbox");

        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.shopService.getShop(Long.parseLong(value)).getSuppliers().add(supplier);
                this.shopService.saveShop(this.shopService.getShop(Long.parseLong(value)));
            } //Add the supplier to the shop suppliers list
        }

        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.supplierService.getSupplier(supplier.getId()).getShops().add(shopService.getShop(Long.parseLong(value)));
                this.supplierService.saveSupplier(supplier);
            } //Add the shop to the supplier shops list
        }

        return "redirect:/suppliers/"+id;
    }

    @GetMapping("/suppliers/{id}/removeShopsToSupplier")
    public String removeShopsToSupplier(Model model, @PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);

        model.addAttribute("shops",supplier.getShops()); //You can only remove shops that are working with that supplier
        model.addAttribute("idSupplier",supplier.getId());

        return "removeShopsToSupplier";
    }

    @GetMapping("/suppliers/{id}/redirectRemoveShopsToSupplier")
    public String redirectRemoveShopsToSupplier(HttpServletRequest request,@PathVariable long id){
        Supplier supplier = this.supplierService.getSupplier(id);
        String[] selectedValues = request.getParameterValues("checkbox");

        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.shopService.getShop(Long.parseLong(value)).getSuppliers().remove(supplier);
                this.shopService.saveShop(this.shopService.getShop(Long.parseLong(value)));
            } //Remove the supplier to the shop suppliers list
        }

        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.supplierService.getSupplier(supplier.getId()).getShops().remove(shopService.getShop(Long.parseLong(value)));
                this.supplierService.saveSupplier(supplier);
            } //Remove the shop to the supplier shops list
        }

        return "redirect:/suppliers/"+id;
    }

}