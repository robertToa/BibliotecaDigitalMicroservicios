package com.biblioteca_digital.svc_prestamos.controller;

import com.biblioteca_digital.svc_prestamos.dto.PrestamoRequest;
import com.biblioteca_digital.svc_prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @PostMapping("/")
    public Map<String, Object> createPrestamo(@Valid @RequestBody PrestamoRequest prestamoRequest){
        return prestamoService.crearPrestamo(prestamoRequest);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getPrestamo(@PathVariable String id){
        return prestamoService.obtenerPrestamo(id);
    }

    @PutMapping("/{id}/devolver")
    public Map<String, Object> updatePrestamo(@PathVariable String id){
        return prestamoService.updatePrestamoDevuelto(id);
    }

    @GetMapping("/health")
    public Map<String, String> health(){
        return Map.of("servicio", "svc-prestamo", "estado", "OK");
    }

}
