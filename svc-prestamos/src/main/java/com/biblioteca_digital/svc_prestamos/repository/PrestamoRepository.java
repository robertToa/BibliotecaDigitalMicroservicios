package com.biblioteca_digital.svc_prestamos.repository;

import com.biblioteca_digital.svc_prestamos.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, String> {
}
