package com.biblioteca_digital.svc_miembros.controller;

import com.biblioteca_digital.svc_miembros.dto.MiembroRequest;
import com.biblioteca_digital.svc_miembros.service.MiembroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MiembroController {
    @Autowired
    private MiembroService miembroService;

    @PostMapping("/")
    public Map<String, Object> createMiembro(@Valid @RequestBody MiembroRequest miembroRequest){
        return miembroService.crearMiembro(miembroRequest);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getMiembro(@PathVariable String id){
        return miembroService.obtenerMiembro(id);
    }

    @GetMapping("/health")
    public Map<String, String> health(){
        return Map.of("servicio", "svc-miembro", "estado", "OK");
    }

}
