package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Supplier;
import es.ssdd.Practica.Repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public SupplierService(){
    }

    public Supplier saveSupplier(Supplier supplier) {
       this.supplierRepository.save(supplier);
       return supplier;
    }

    public Supplier getSupplier(long id) {
        if (this.supplierRepository.existsById(id))
            return this.supplierRepository.getById(id);
        return null;
    }

    public Collection<Supplier> getSuppliers() {
        return this.supplierRepository.findAll();
    }

    @Transactional
    public Supplier deleteSupplier(long id) {
        if (this.supplierRepository.existsById(id)) {
            Supplier supplier = this.supplierRepository.findById(id).get();
            this.supplierRepository.deleteById(id);
            return supplier;
        }
        return null;
    }

    @Transactional
    public Supplier modifySupplier(long id, Supplier modifiedSupplier) {
        if (this.supplierRepository.existsById(id)) {
            Supplier supplier = this.supplierRepository.findById(id).get();
            supplier.setName(modifiedSupplier.getName());
            supplier.setDescription(modifiedSupplier.getDescription());
            this.supplierRepository.save(supplier);
            return supplier;
        }
        return null;
    }

    /*public void addShop(Shop shop, long idSupplier){
        if (this.supplierRepository.existsById(idSupplier)){
            this.supplierRepository.findById(idSupplier).get().getShops().add(shop);
            this.supplierRepository.save(this.supplierRepository.findById(idSupplier).get());
        }
    }*/
}