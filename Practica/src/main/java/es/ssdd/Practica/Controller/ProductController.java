package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import es.ssdd.Practica.Services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ShopService shopService;

    @Autowired
    CompositionService compositionService;


    @GetMapping("/shops/{idShop}/products")
    public String getShopProducts(Model model, @PathVariable long idShop){
        Shop shop = shopService.getShop(idShop);
        model.addAttribute("products",shop.getProducts());
        model.addAttribute("idShop", shop.getId());
        model.addAttribute("shopName", shop.getName());
        return "showProducts";
    }

    @GetMapping("/shops/{idShop}/products/{idProduct}")
    public String getProduct(Model model, @PathVariable long idShop, @PathVariable long idProduct) {
        Product product = this.productService.getProduct(idProduct);
        Shop shop = this.shopService.getShop(idShop);
        Composition composition = product.getComposition();

        model.addAttribute("nameP", product.getName());
        model.addAttribute("prize", product.getPrize());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("idProduct", product.getId());

        model.addAttribute("idShop",shop.getId());
        model.addAttribute("nameShop", shop.getName());

        if(composition!=null) { //If product has composition, the add composition button is not shown
            model.addAttribute("existsComposition", true);
            model.addAttribute("content",composition.getContent());
        }
        else {
            model.addAttribute("existsComposition", false);
            model.addAttribute("content","This product hasn't got a composition yet");
        }

        return "viewProduct";
    }

    @GetMapping("/shops/{idShop}/newProduct")
    public String newProduct(Model model, @PathVariable long idShop) {
        Shop shop = this.shopService.getShop(idShop);
        model.addAttribute("idShop",shop.getId());
        return "newProduct";
    }

    @GetMapping("/shops/{idShop}/redirectNewProduct")
    public String newProduct(@PathVariable long idShop, @RequestParam String name, @RequestParam float prize, @RequestParam String description, @RequestParam String image) {
        if (name.length() == 0)
            return "redirect:/error";
        if (image.length() == 0)
            image = "/assets/img/new.jpg"; //Set default image
        //Create new product with the information received in the forms
        Product product = new Product(name, description, prize, null, image, idShop);
        this.productService.createProduct(product, idShop);
        this.shopService.getShop(idShop).getProducts().add(product);
        return "redirect:/shops/{idShop}/products";
    }

    @GetMapping("/shops/{idShop}/products/{id}/deleteProduct")
    public String deleteProduct(@PathVariable long id, @PathVariable long idShop) {
        Product product = this.productService.getProduct(id);
        if (product == null || this.shopService.getShop(idShop) == null || !this.shopService.getShop(idShop).getProducts().contains(product)) {
            return "redirect:/error";
        }

        // Al borrar un producto, con CascadeType.All se borra sola la composition (QUITAR)
        this.shopService.getShop(idShop).getProducts().remove(product);
        if (product.getComposition() != null) //If product has composition, also delete the composition
            this.compositionService.deleteComposition(product.getComposition().getId());
        this.productService.deleteProduct(id);

        return "redirect:/shops/{idShop}/products";
    }

    @GetMapping("/shops/{idShop}/products/{id}/modifyProduct")
    public String modifyProduct(Model model, @PathVariable long id, @PathVariable long idShop){
        Product product = this.productService.getProduct(id);
        //Send the data to HTML of the product that you want to modify
        model.addAttribute("idShop",idShop);
        model.addAttribute("name", product.getName());
        model.addAttribute("prize", product.getPrize());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("id", product.getId());
        return "modifyProduct";
    }

   @GetMapping("/shops/{idShop}/products/redirectModifyProduct")
    public String redirectModifyProduct(@RequestParam("id") long id,@RequestParam("name") String name, @RequestParam("prize") float prize,
                                 @RequestParam("description") String description,@RequestParam("image") String image, @PathVariable long idShop){
        //Receive new data and modify the product
        this.productService.modifyProduct(id, idShop, new Product(name, description, prize,null, image, idShop));
        return "redirect:/shops/"+idShop+"/products/"+id;
    }



}
