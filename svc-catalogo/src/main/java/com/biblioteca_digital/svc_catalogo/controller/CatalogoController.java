package com.biblioteca_digital.svc_catalogo.controller;

import com.biblioteca_digital.svc_catalogo.dto.CatalogoRequest;
import com.biblioteca_digital.svc_catalogo.service.CatalogoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @PostMapping("/save")
    public Map<String, Object> createCatalogo(@Valid @RequestBody CatalogoRequest catalogoRequest){
        return catalogoService.crearCatalogo(catalogoRequest);
    }

    @GetMapping("/{isbn}")
    public Map<String, Object> getCatalogoPorIsbn(@PathVariable String isbn){
        return catalogoService.obtenerLibroPorIsbn(isbn);
    }

    @GetMapping("/health")
    public Map<String, String> health(){
        return Map.of("servicio", "svc-catalogo", "estado", "OK");
    }
}
