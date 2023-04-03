package es.ssdd.Practica.Controller;

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
        model.addAttribute("products",shop.getProducts()); //Estas 2 si sobran luego se quitan
        model.addAttribute("suppliers",shop.getSuppliers());

        return "viewShop";
    }



    @GetMapping("shops/newShop")
    public String newShop(@RequestParam String name, @RequestParam String direction,@RequestParam String image){ //No le meto products ni suppliers, lo hacemos en otro sitio
        if (name.length() == 0)
            return "redirect:/error";
        if (image.length() == 0)
            image = "/assets/img/question_mark_PNG68.png";
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
        if(productList.size()>0){
            for (Product product:productList)
                this.productService.deleteProduct(product.getId());//Se deletean todos los productos asociados a esa tienda
        }
        Collection<Supplier> suppliersList = this.supplierService.getSuppliers();
        if(suppliersList.size()>0){
            for(Supplier supplier : suppliersList){ //Por cada supplier
                for(Shop shopAux : supplier.getShops()) { //Recorrer las tiendas que reparte
                    if (Objects.equals(shop.getId(), shopAux.getId())){ //Si el supplier reparte a la tienda que se va a borrar...
                        suppliersList.remove(shopAux); //Eliminar la tienda de la lista del supplier
                    }
                }
            }
        }
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
        return "redirect:/shop/" + idShop;
    }



}
