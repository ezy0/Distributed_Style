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

   /* @PostMapping("/shops/{id}/{idP}/newComposition")
    public ResponseEntity<Composition> createComposition(@PathVariable long idP, @RequestBody Composition composition) {
        this.compositionService.createComposition(composition, idP);
        if (this.compositionService.getComposition(composition.getId()) == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(composition, HttpStatus.OK);
    }*/

}
