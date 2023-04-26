package es.ssdd.Practica.Repositories;

import es.ssdd.Practica.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByName(String name);
    Optional<Supplier> findByDescription(String description);
}
