package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", this.productService.getProducts());
        return "showProducts";
    }
}
