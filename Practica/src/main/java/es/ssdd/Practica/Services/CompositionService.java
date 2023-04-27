package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;

import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Repositories.CompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;

    public CompositionService(){
    }

    public Composition createComposition1(Composition composition) {
        return this.compositionRepository.save(composition);
    }
    public Composition createComposition(Composition composition, long idProduct) {
        composition.setId(idProduct);
        composition.setProductId(idProduct);
        return this.compositionRepository.save(composition);
    }

    public Collection<Composition> getCompositions(){
        return this.compositionRepository.findAll();
    }

    public Composition getComposition(long id) {
        if (this.compositionRepository.existsById(id)) {
            return this.compositionRepository.findById(id).get();
        }
        return null;
    }

    @Transactional
    public Composition deleteComposition(long id){
        if (this.compositionRepository.existsById(id)) {
            Composition composition = this.compositionRepository.findById(id).get();
            this.compositionRepository.deleteById(id);
            return composition;
        }
        return null;
    }

    @Transactional
    public Composition modifyComposition(long id, Composition modifiedComposition){
        if (this.compositionRepository.existsById(id)) {
            Composition composition = this.compositionRepository.findById(id).get();
            composition.setContent(modifiedComposition.getContent());
            this.compositionRepository.save(composition);
            return composition;
        }
        return null;
    }
}
