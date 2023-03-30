package es.ssdd.Practica.RESTController;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Services.CompositionService;
import es.ssdd.Practica.Services.ProductService;
import org.apache.coyote.Response;
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
        if (this.productService.getProduct(idP) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        this.compositionService.createComposition(composition, idP);
        this.productService.getProduct(idP).setComposition((composition));
        return new ResponseEntity<>(composition, HttpStatus.OK);
    }

    @PutMapping("/shops/{id}/{idP}/modifyComposition")
    public ResponseEntity<Composition> modifyComposition(@PathVariable long idP, @RequestBody Composition composition) {
        return new ResponseEntity<>(this.compositionService.modifyComposition(idP, composition), HttpStatus.OK);
    }

    @DeleteMapping("/shops/{id}/{idP}/deleteComposition")
    public ResponseEntity<Composition> deleteComposition(@PathVariable long idP, @PathVariable long id){
        Composition composition = this.compositionService.deleteComposition(idP);
        this.productService.getProduct(idP).setComposition(null);
        return new ResponseEntity<>(composition, HttpStatus.OK);
    }
}
