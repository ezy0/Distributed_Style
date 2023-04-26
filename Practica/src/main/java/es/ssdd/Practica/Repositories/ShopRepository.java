package es.ssdd.Practica.Repositories;

import es.ssdd.Practica.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Shop;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByName(String name);
    Optional<Shop> findByDirection(String direction);
}
