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
public class CompositionController {
    @Autowired
    CompositionService compositionService;
    @Autowired
    ProductService productService;
    @Autowired
    ShopService shopService;

    @GetMapping("shops/{idShop}/products/{idProduct}/newComposition")
    public String newComposition(Model model, @PathVariable long idShop, @PathVariable long idProduct) {
        Shop shop = this.shopService.getShop(idShop);
        Product product = this.productService.getProduct(idProduct);

        model.addAttribute("shopName",shop.getName());
        model.addAttribute("productName",product.getName());

        model.addAttribute("idShop",idShop);
        model.addAttribute("idProduct",idProduct);

        return "newComposition";
    }

    @GetMapping("shops/{idShop}/products/{idProduct}/redirectNewComposition")
    public String newComposition(@PathVariable long idShop, @PathVariable long idProduct,@RequestParam String content){
        //Create new composition with the information received in the forms
        Product product = productService.getProduct(idProduct);
        product.setComposition(this.compositionService.saveComposition(new Composition(content), idProduct));
        this.productService.saveProduct(product);

        return "redirect:/shops/"+idShop+"/products/"+idProduct;
    }

    @GetMapping("/shops/{idShop}/products/{idProduct}/deleteComposition")
    public String deleteComposition(@PathVariable long idShop, @PathVariable long idProduct){
        if (productService.getProduct(idProduct).getComposition() == null)
            return "redirect:/error";
        Composition composition = productService.getProduct(idProduct).getComposition();
        productService.getProduct(idProduct).setComposition(null);
        compositionService.deleteComposition(composition.getId());
        productService.saveProduct(productService.getProduct(idProduct));

        return "redirect:/shops/"+idShop+"/products/"+idProduct;
    }

    @GetMapping("/shops/{idShop}/products/{idProduct}/modifyComposition")
    public String modifyComposition(Model model, @PathVariable long idShop, @PathVariable long idProduct){
        Composition composition = this.productService.getProduct(idProduct).getComposition();
        if (composition == null)
            return "redirect:/error";
        //Send the data to HTML of the composition that you want to modify
        model.addAttribute("idShop",idShop);
        model.addAttribute("idProduct",idProduct);

        model.addAttribute("content",composition.getContent());

        return "modifyComposition";
    }

    @GetMapping("/shops/{idShop}/products/{idProduct}/redirectModifyComposition")
    public String redirectModifyShop(@PathVariable long idProduct, @PathVariable long idShop,@RequestParam("content") String content){
        //Receive new data and modify the composition
        Product product = productService.getProduct(idProduct);
        this.compositionService.modifyComposition(product.getComposition().getId(),new Composition(content));

        return "redirect:/shops/"+idShop+"/products/"+idProduct;
    }
}
