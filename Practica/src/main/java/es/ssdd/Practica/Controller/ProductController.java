package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    // METODOS GET
    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", this.productService.getProducts());
        return "showProducts";
    }

    @GetMapping("/products/{id}")
    public String getProduct(Model model, @PathVariable int id) {
        Product producto = productService.getProduct(id);

        model.addAttribute("name", producto.getName());
        model.addAttribute("prize", producto.getPrize());
        model.addAttribute("image", producto.getImage());
        model.addAttribute("description", producto.getDescription());
        model.addAttribute("id", producto.getId());

        return "viewProduct";
    }

    @GetMapping("shops/{id}/newProduct")
    public String newProduct(@PathVariable long id, @RequestParam String name, @RequestParam float prize, @RequestParam String description, @RequestParam String image) {
        if (name.length() == 0)
            return "redirect:/error";
        if (image.length() == 0)
            image = "../assets/img/sudadera.png";
        Product product = new Product(name, description, prize, null, image, (long)1);
        productService.createProduct(product, id);
        return "redirect:/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        Product product = this.productService.deleteProduct(id);
        if (product == null) {
            return "redirect:/error";
        }
        return "deletedProduct";
    }

    @GetMapping("/products/modifyProduct/{id}")
    public String modifyProduct(Model model, @PathVariable long id){
        Product product = this.productService.getProduct(id);
        //model.addAttribute("product",product);
        model.addAttribute("name", product.getName());
        model.addAttribute("prize", product.getPrize());
        model.addAttribute("image", product.getImage());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("id", product.getId());
        return "modifyProduct";
    }
    /*@GetMapping("/products/redirectModify")
    public String redirectModify(@RequestParam("id") long id,@RequestParam("name") String name, @RequestParam("prize") float prize,
                                 @RequestParam("description") String description,@RequestParam("image") String image){
        Product product = this.productService.modifyProduct(id,new Product(name, description, prize,null, image, (long) 1);
        return "redirect:/products/" + id;
    }*/



}
