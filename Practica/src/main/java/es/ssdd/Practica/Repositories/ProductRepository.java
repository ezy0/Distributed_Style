package es.ssdd.Practica.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import es.ssdd.Practica.Models.Product;

import java.util.List;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /*List<Product> findByName(String name);
    List<Product> findByNameIgnoreCase(String name);
    List<Product> findAllOrderByNameAsc();
    List<Product> findAllOrderByNameDesc();

    List<Product> findByDescription(String description);
    List<Product> findByDescriptionIgnoreCase(String description);
    List<Product> findAllOrderByDescriptionAsc();
    List<Product> findAllOrderByDescriptionDesc();

    List<Product> findByPrize(float prize);
    List<Product> findAllOrderByPrizeAsc();
    List<Product> findAllByOrderByPrizeDesc();

    List<Product> findByShopId(long shopId);
    List<Product> findAllOrderByShopIdAsc();
    List<Product> findAllOrderByShopIdDesc();*/

}

