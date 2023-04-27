package es.ssdd.Practica.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);
    Optional<Product> findByDescription(String description);
    Optional<Product> findByPrize(float prize);
    List<Product> findByShopId(long shopId);
}

