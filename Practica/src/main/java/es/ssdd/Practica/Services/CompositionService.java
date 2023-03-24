package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CompositionService {

    private HashMap<Long, Composition> compositions = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public Composition deleteComposition(long id){
        Composition composition = compositions.remove(id);
        if (composition != null) {
            return composition;
        }
        return null;
    }

}
