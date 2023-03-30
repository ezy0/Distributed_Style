package es.ssdd.Practica.Services;

import es.ssdd.Practica.Models.Composition;
import es.ssdd.Practica.Models.Product;
import es.ssdd.Practica.Models.Supplier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import es.ssdd.Practica.Models.Shop;


@Service
public class SupplierService {

    private HashMap<Long, Supplier> suppliers = new HashMap<>();
    private AtomicLong lastId = new AtomicLong();

    public SupplierService(){
    }

    public Collection<Supplier> getSuppliers(){
        return this.suppliers.values().stream().toList();
    }

    public Supplier getSupplier(long id){
        if (this.suppliers.containsKey(id)) {
            return this.suppliers.get(id);
        }
        return null;
    }

    public Supplier deleteSupplier(long id){
        if (this.suppliers.containsKey(id)) {
            return suppliers.remove(id);
        }
        return null;
    }

    public Supplier createSupplier(Supplier supplier){
        supplier.setId(lastId.incrementAndGet());
        suppliers.put(lastId.get(), supplier);
        return supplier;
    }

    public Supplier modifySupplier(long id, Supplier modifiedSupplier) {
        Supplier supplier = this.getSupplier(id);
        if (supplier == null)
            return null;
        supplier.setName(modifiedSupplier.getName());
        supplier.setDescription(modifiedSupplier.getDescription());
        return supplier;
    }




}
