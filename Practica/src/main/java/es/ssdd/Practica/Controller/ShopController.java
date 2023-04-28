package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Repositories.SupplierRepository;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import es.ssdd.Practica.Services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Supplier;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Controller
public class ShopController {

    @Autowired
    ShopService shopService;
    @Autowired
    ProductService productService;
    @Autowired
    SupplierService supplierService;
    @Autowired
    CompositionService compositionService;

    @GetMapping("/shops")
    public String getShops(Model model){
        model.addAttribute("shops", this.shopService.getShops());
        return "showShops";
    }

    @GetMapping("/shops/{id}")
    public String getShop(Model model, @PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        model.addAttribute("id",shop.getId());
        model.addAttribute("name",shop.getName());
        model.addAttribute("image",shop.getImage());
        model.addAttribute("direction",shop.getDirection());
        model.addAttribute("products",shop.getProducts());
        model.addAttribute("suppliers",shop.getSuppliers());

        return "viewShop";
    }

    @GetMapping("shops/newShop")
    public String newShop(@RequestParam String name, @RequestParam String direction, @RequestParam String image){
        if (name.length() == 0)
            return "redirect:/error";
        if (image.length() == 0)
            image = "/assets/img/new.jpg"; //Set default image
        //Create new shop with the information received in the forms
        Shop shop = new Shop(name,image, direction);
        shopService.createShop(shop);
        return "redirect:/shops";
    }

    @GetMapping("/shops/{id}/delete")
    public String deleteShop(@PathVariable long id){
        Shop shop = this.shopService.deleteShop(id);
        if (shop == null) {
            return "redirect:/error";
        }
        if (shop.getSuppliers() != null && shop.getProducts().size() > 0)
            for (Product product : shop.getProducts()) //All products in the shop are removed
                for (Product product2 : this.productService.getProducts())
                    if (Objects.equals(product.getId(), product2.getId())){
                        if (product2.getComposition() != null && this.compositionService.getComposition(product2.getComposition().getId()) != null)
                            this.compositionService.deleteComposition(product2.getComposition().getId()); //Also eliminate the composition of each product, in case the product has a composition
                        this.productService.deleteProduct(product2.getId());
                    }
        if (shop.getSuppliers() != null && !shop.getSuppliers().isEmpty())
            for (Supplier supplier : shop.getSuppliers()) //The shop is deleted from the list of suppliers' shops
                for (Supplier supplier2 : this.supplierService.getSuppliers())
                    if (Objects.equals(supplier.getId(), supplier2.getId()))
                        this.supplierService.getSupplier(supplier.getId()).getShops().remove(shop);
        return "redirect:/shops";
    }

    @GetMapping("/shops/{id}/modifyShop")
    public String modifyShop(Model model, @PathVariable long id){ //Send the data to HTML of the shop that you want to modify
        Shop shop = this.shopService.getShop(id);
        model.addAttribute("id",shop.getId());
        model.addAttribute("name",shop.getName());
        model.addAttribute("image",shop.getImage());
        model.addAttribute("direction",shop.getDirection());
        return "modifyShop";
    }

    @GetMapping("/shops/redirectModifyShop")
    public String redirectModifyShop(@RequestParam("idShop") long idShop,@RequestParam("name") String name, @RequestParam("image") String image,
                                 @RequestParam("direction") String direction){ //Receive new data and modify the shop
        this.shopService.modifyShop(idShop,new Shop(name, image, direction));
        return "redirect:/shops/"+idShop;
    }

    @GetMapping("/shops/{id}/addSuppliersToShop")
    public String addSuppliersToShop(Model model, @PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        Collection<Supplier> newSuppliers = new ArrayList<>(); //Auxiliary list to save the suppliers to be displayed

        //You can only add suppliers that are not working with that shop
        for (Supplier supplier : this.supplierService.getSuppliers())
            if (!shop.getSuppliers().contains(supplier))
                newSuppliers.add(supplier);

        model.addAttribute("suppliers",newSuppliers); //newSuppliers is now the correct list
        model.addAttribute("idShop",shop.getId());

        return "addSuppliersToShop";
    }

    @GetMapping("/shops/{id}/redirectAddSuppliersToShop")
    public String redirectAddSuppliersToShop(HttpServletRequest request, @PathVariable long id){
        Shop shop = this.shopService.getShop(id);
        Supplier supplier;
        String[] selectedValues = request.getParameterValues("checkbox");

        if (selectedValues != null) {
            for (String value : selectedValues) {
                supplier = this.supplierService.getSupplier(Long.parseLong(value));
                supplier.getShops().add(shop);
                this.supplierService.createSupplier(supplier);
            }   //Add the shop to the supplier shops list
        }

        if (selectedValues != null) {
            for (String value : selectedValues) {
                supplier = this.supplierService.getSupplier(Long.parseLong(value));
                shop.getSuppliers().add(supplier);
                this.shopService.createShop(shop);
            } //Add the supplier to the shop suppliers list
        }

        return "redirect:/shops/"+id;
    }

    @GetMapping("/shops/{id}/removeSuppliersToShop")
    public String removeSuppliersToShop(Model model, @PathVariable long id){

        Shop shop = this.shopService.getShop(id);

        model.addAttribute("suppliers",shop.getSuppliers());//You can only remove suppliers that are working with that shop
        model.addAttribute("idShop",shop.getId());

        return "removeSuppliersToShop";
    }

    @GetMapping("/shops/{id}/redirectRemoveShopsToSupplier")
    public String redirectRemoveShopsToSupplier(HttpServletRequest request, @PathVariable long id){
        Shop shop = this.shopService.getShop(id);


        String[] selectedValues = request.getParameterValues("checkbox");

        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.supplierService.getSupplier(Long.parseLong(value)).getShops().remove(shop);
            } //Remove the shop to the supplier shops list
        }

        if (selectedValues != null) {
            for (String value : selectedValues) {
                this.shopService.getShop(shop.getId()).getSuppliers().remove(supplierService.getSupplier(Long.parseLong(value)));
            } //Remove the supplier to the shop suppliers list
        }

        return "redirect:/shops/"+id;
    }

}
