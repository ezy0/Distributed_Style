package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CompositionService {

    private HashMap<Long, Composition> compositions = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    /*@Autowired
    ProductService productService;*/

    public CompositionService(){
    }

    public Composition getComposition(long id) {
        if (this.compositions.containsKey(id)) {
            return this.compositions.get(id);
        }
        return null;
    }

    public Composition deleteComposition(long id){
        Composition composition = compositions.remove(id);
        /*if (composition == null) {
            return null;
        }
        productService.getProduct(compositions.get(id).getProductId()).setComposition(null);*/
        return composition;
    }

    public Composition createComposition(Composition composition, long idProduct){
        /*composition.setId(lastId.incrementAndGet());
        composition.setProductId(idProduct);
        if (productService.getProduct(idProduct) == null)
            return null;
        if (productService.getProduct(idProduct).getComposition() != null)
            compositions.remove(productService.getProduct(idProduct).getComposition().getId());
        productService.getProduct(idProduct).setComposition(composition);*/
        return compositions.put(lastId.get(), composition);
    }

}
