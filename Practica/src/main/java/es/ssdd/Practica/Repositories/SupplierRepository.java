package es.ssdd.Practica.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
