package es.ssdd.Practica.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

