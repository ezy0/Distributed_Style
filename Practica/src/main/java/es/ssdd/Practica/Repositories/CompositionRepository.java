package es.ssdd.Practica.Repositories;

import es.ssdd.Practica.Models.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompositionRepository extends JpaRepository<Composition, Long> {

    Optional<Composition> findByContent(String content);

}
