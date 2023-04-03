package es.ssdd.Practica.Controller;

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

import java.util.ArrayList;
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
        for (Shop shop : this.shopService.getShops())
            if (shop.getProducts().size() == 0 && shop.getId() <= 3) {
                Product product;
                if (shop.getId() == 1)
                    product = new Product("Nike Air Jordan", "Nike Air Jordan 1 Black", 100F, null, "/assets/img/locker.jpg", shop.getId());
                else if (shop.getId() == 2)
                    product = new Product("Nude Sweater", "Nude Project brow sweater", 69.99F, null, "/assets/img/nude.jpg", shop.getId());
                else
                    product = new Product("White Sneakers", "White sneakers from Martin Valen", 79.99F, null, "/assets/img/martin.jpg", shop.getId());
                this.productService.createProduct(product, shop.getId());
                if (product.getId() < 4)
                    this.shopService.getShop(shop.getId()).getProducts().add(product);
                else
                    this.productService.deleteProduct(product.getId());
            }
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
        model.addAttribute("products",shop.getProducts()); //Estas 2 si sobran luego se quitan
        model.addAttribute("suppliers",shop.getSuppliers());

        return "viewShop";
    }


    @GetMapping("shops/newShop")
    public String newShop(@RequestParam String name, @RequestParam String direction,@RequestParam String image){ //No le meto products ni suppliers, lo hacemos en otro sitio
        if (name.length() == 0)
            return "redirect:/error";
        if (image.length() == 0)
            image = "/assets/img/shop.png";
        Shop shop = new Shop(name,image,direction);
        shopService.createShop(shop);
        return "redirect:/shops";
    }

    @GetMapping("/shops/{id}/delete")
    public String deleteShop(@PathVariable long id){
        Shop shop = this.shopService.deleteShop(id);
        if (shop == null) {
            return "redirect:/error";
        }
        ArrayList<Product> productList = shop.getProducts();
        if (shop.getSuppliers() != null && shop.getProducts().size() > 0)
            for (Product product : shop.getProducts())
                for (Product product2 : this.productService.getProducts())
                    if (Objects.equals(product.getId(), product2.getId())){
                        if (product2.getComposition() != null && this.compositionService.getComposition(product2.getComposition().getId()) != null)
                            this.compositionService.deleteComposition(product2.getComposition().getId());
                        this.productService.deleteProduct(product2.getId());
                    }
        if (shop.getSuppliers() != null && shop.getSuppliers().size() > 0)
            for (Supplier supplier : shop.getSuppliers())
                for (Supplier supplier2 : this.supplierService.getSuppliers())
                    if (Objects.equals(supplier.getId(), supplier2.getId()))
                        this.supplierService.getSupplier(supplier.getId()).getShops().remove(this.shopService.getShop(id));
        return "redirect:/shops";
    }

    @GetMapping("/shops/{id}/modifyShop")
    public String modifyShop(Model model, @PathVariable long id){ //Esto sirve para mostrar los datos de la tienda que se va a modificar
        Shop shop = this.shopService.getShop(id);
        model.addAttribute("id",shop.getId());
        model.addAttribute("name",shop.getName());
        model.addAttribute("image",shop.getImage());
        model.addAttribute("direction",shop.getDirection());
        return "modifyShop";
    }

    @GetMapping("/shops/redirectModifyShop") //Este metodo sirve para recoger los datos enviados al hacer el submit con el objetivo de usarlos para modificar la shop. Te redirige a la shop ya modificada
    public String redirectModifyShop(@RequestParam("idShop") long idShop,@RequestParam("name") String name, @RequestParam("image") String image,
                                 @RequestParam("direction") String direction){
        this.shopService.modifyShop(idShop,new Shop(name, image, direction)); //La id no dejará cambiarla
        return "redirect:/shops/"+idShop;
    }



}
