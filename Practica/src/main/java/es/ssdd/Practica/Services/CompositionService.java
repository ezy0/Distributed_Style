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

    public CompositionService(){
    }

    public Collection<Composition> getCompositions(){
        return this.compositions.values().stream().toList();
    }

    public Composition getComposition(long id) {
        if (this.compositions.containsKey(id)) {
            return this.compositions.get(id);
        }
        return null;
    }

    public Composition deleteComposition(long id){
        if (this.compositions.containsKey(id)) {
            return compositions.remove(id);
        }
        return null;
    }

    public Composition createComposition(Composition composition, long idProduct){
        composition.setId(lastId.incrementAndGet());
        composition.setProductId(idProduct);
        return compositions.put(lastId.get(), composition);
    }

    public Composition modifyComposition(long id, Composition modifiedComposition){
        Composition composition = this.getComposition(id);
        if (composition == null)
            return null;
        composition.setContent(modifiedComposition.getContent());
        return composition;
    }

}
