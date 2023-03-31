package es.ssdd.Practica.Controller;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
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

    @PostMapping("shops/{id}/{idP}/newComposition")
    public String newShop(@RequestParam String content, @PathVariable long id, @PathVariable long idP) {
        Composition composition = new Composition(content);
        productService.getProduct(composition.getProductId()).setComposition(composition);
        return "showProducts";
    }

    @DeleteMapping("/shops/{id}/{idP}/deleteComposition")
    public String deleteComposition(@PathVariable long id, @PathVariable long idP){
        if (productService.getProduct(idP).getComposition() == null)
            return "redirect:/error";
        Composition composition = productService.getProduct(idP).getComposition();
        productService.getProduct(idP).setComposition(null);
        compositionService.deleteComposition(composition.getId());
        return "viewProduct";
    }

    @GetMapping("/shops/{id}/{idP}/modifyComposition")
    public String modifyComposition(Model model, @PathVariable long id, @PathVariable long idP){
        Composition composition = this.compositionService.getComposition(id);

        model.addAttribute("content",composition.getContent());

        return "modifyComposition";
    }

    @PutMapping("/shops/{id}/{idP}/redirectModifyComposition")
    public String redirectModifyShop(@RequestParam("idComposition") long idComposition,@RequestParam("content") String content){
        this.compositionService.modifyComposition(idComposition,new Composition(content));
        return "redirect:/shop/{id}/{idP}/";
    }
}
