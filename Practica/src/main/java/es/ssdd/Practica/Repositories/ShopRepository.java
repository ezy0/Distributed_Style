package es.ssdd.Practica.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
