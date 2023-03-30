package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class CompositionRESTController {

    @Autowired
    CompositionService compositionService;
    @Autowired
    ProductService productService;

    @GetMapping("/compositions")
    public ResponseEntity<Collection<Composition>> getAllCompositions(){
        return new ResponseEntity<>(this.compositionService.getCompositions(),HttpStatus.OK);
    }

    @GetMapping("/shops/{id}/{idP}/composition")
    public ResponseEntity<Composition> getComposition(@PathVariable long idP){
        if (this.productService.getProduct(idP) == null || this.productService.getProduct(idP).getComposition() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Composition composition = this.compositionService.getComposition(this.productService.getProduct(idP).getComposition().getId());
        if (composition == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(composition, HttpStatus.OK);
    }

    @PostMapping("/shops/{id}/{idP}/newComposition")
    public ResponseEntity<Composition> createComposition(@PathVariable long idP, @RequestBody Composition composition) {
        Composition newComposition = this.compositionService.createComposition(composition, idP);
        if (this.productService.getProduct(idP).getComposition() != null)
            this.productService.getProduct(idP).setComposition((newComposition));
        return new ResponseEntity<>(newComposition, HttpStatus.OK);
    }

}
