package es.ssdd.Practica.Repositories;

import es.ssdd.Practica.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Shop;
import java.util.List;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByName(String name);
    List<Shop> findByNameIgnoreCase(String name);

    List<Shop> findByDirection(String direction);
    List<Shop> findByDirectionIgnoreCase(String direction);


}
