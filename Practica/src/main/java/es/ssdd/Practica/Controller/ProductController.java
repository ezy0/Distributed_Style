package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", this.productService.getProducts());
        return "showProducts";
    }

    @GetMapping("/products/{num}")
    public String getProduct(Model model, @PathVariable int num) {
        Product producto = productService.getProduct(num - 1);

        model.addAttribute("name", producto.getName());
        model.addAttribute("prize", producto.getPrize());
        model.addAttribute("image", producto.getImage());
        model.addAttribute("description", producto.getDescription());

        return "viewProduct";
    }
}
