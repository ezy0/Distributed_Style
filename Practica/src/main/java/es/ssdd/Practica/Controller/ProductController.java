package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Shop;
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

    // METODOS GET

    /* Esto sería en el caso de que queramos mostrar TODOS los productos de TODAS las tiendas
    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", this.productService.getProducts());
        return "showProducts";
    }
*/
    @GetMapping("/shops/{idShop}/products")
    public String getShopProducts(Model model, @PathVariable long idShop){
        Shop shop = shopService.getShop(idShop);
        model.addAttribute("products", this.productService.getProductsShop(idShop));
        model.addAttribute("idShop", shop.getId()); //Se necesita para usarla en la url que redirige al new product dentro de una tienda
        return "showProducts";
    }

    @GetMapping("/shops/{idShop}/products/{idProduct}")     //"/products/{id}" ANTES
    public String getProduct(Model model, @PathVariable long idShop, @PathVariable long idProduct) {
        Product producto = this.productService.getProduct(idProduct);
        Shop shop = this.shopService.getShop(idShop);

        model.addAttribute("nameP", producto.getName());
        model.addAttribute("prize", producto.getPrize());
        model.addAttribute("image", producto.getImage());
        model.addAttribute("description", producto.getDescription());
        model.addAttribute("idProduct", producto.getId());

        model.addAttribute("idShop",shop.getId());
        model.addAttribute("nameShop", shop.getName());

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
            image = "/assets/img/sudadera.png";
        Product product = new Product(name, description, prize, null, image, idShop);
        productService.createProduct(product, idShop);
        return "redirect:/shops/{idShop}/products";
    }

    @GetMapping("/shops/{idShop}/products/{id}/delete")
    public String deleteProduct(@PathVariable long id) {
        Product product = this.productService.deleteProduct(id);
        if (product == null) {
            return "redirect:/error";
        }
        return "redirect:/shops/{idShop}/products";
    }

    @GetMapping("/shops/{idShop}/products/{id}/modifyProduct")
    public String modifyProduct(Model model, @PathVariable long id){
        Product product = this.productService.getProduct(id);
        //model.addAttribute("product",product);
        model.addAttribute("name", product.getName());
        model.addAttribute("prize", product.getPrize());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("id", product.getId());
        return "redirect:/shops/{idShop}/products/{id}";
    }
   /* @GetMapping("/products/redirectModifyProduct")
    public String redirectModifyProduct(@RequestParam("id") long id,@RequestParam("name") String name, @RequestParam("prize") float prize,
                                 @RequestParam("description") String description,@RequestParam("image") String image){
        this.productService.modifyProduct(id,new Product(name, description, prize,null, image, (long) 1);
        return "redirect:/products/" + id;
    }*/



}
