package com.biblioteca_digital.svc_catalogo.repository;

import com.biblioteca_digital.svc_catalogo.model.Catalogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogoRepository extends JpaRepository<Catalogo, Long> {
    Optional<Catalogo> findByIsbn(String isbn);
}
