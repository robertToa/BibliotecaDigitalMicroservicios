package com.biblioteca_digital.svc_miembros.service;

import com.biblioteca_digital.svc_miembros.dto.MiembroRequest;
import com.biblioteca_digital.svc_miembros.model.Miembro;
import com.biblioteca_digital.svc_miembros.repository.MiembroRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MiembroService {
    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Object> crearMiembro(MiembroRequest miembroRequest){
        Miembro miembro = new Miembro();
        String id = "M-" + UUID.randomUUID().toString();
        miembro.setId(id);
        miembro.setNombre(miembroRequest.getNombre());
        miembro.setEmail(miembroRequest.getEmail());
        miembro.setTipoMiembro(miembroRequest.getTipoMiembro());
        miembro.setFechaRegistro(new Date().toString());
        miembro.setPrestamosActivos(miembroRequest.getPrestamosActivos());
        miembroRepository.save(miembro);
        stringRedisTemplate.delete("miembro:all");
        return Map.of("mensaje", "Miembro creado correctamente", "identifciador", miembro.getId());
    }

    public Map<String, Object> obtenerMiembro(String id){
        long tiempoTranscurrido = 0, inicioTiempo = 0, finTiempo= 0;
        String cacheKey = "miembro:"+id;
        inicioTiempo = System.currentTimeMillis();
        String cached = stringRedisTemplate.opsForValue().get(cacheKey);
        finTiempo = System.currentTimeMillis();
        if(cached != null){
            try{
                Map<String, Object> miembroCache = objectMapper.readValue(cached, new TypeReference<Map<String, Object>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
                tiempoTranscurrido = finTiempo - inicioTiempo;
                return Map.of("Fuente", "cache redis ","tiempo en milisegundos", tiempoTranscurrido, "datos", miembroCache);
            } catch (Exception ex){
                System.err.println("error: "+ ex.getMessage());
                stringRedisTemplate.delete(cacheKey);
            }
        }
        inicioTiempo = System.currentTimeMillis();
        Optional<Miembro> miembroDB = miembroRepository.findById(id);
        finTiempo = System.currentTimeMillis();
        if(miembroDB.isEmpty()){
            return Map.of("error", "Miembro no encontrado con el identificador: "+ id);
        }
        Miembro miembroGet = miembroDB.get();
        try{
            String jsonMiembro = objectMapper.writeValueAsString(miembroGet);
            stringRedisTemplate.opsForValue().set(cacheKey, jsonMiembro, 60, TimeUnit.SECONDS);
        }catch (Exception ex){
            System.err.println("Error: "+ ex.getMessage());
        }
        tiempoTranscurrido = finTiempo - inicioTiempo;
        return Map.of("Fuente", "base de datos", "tiempo en milisegundos", tiempoTranscurrido,"datos", miembroGet);
    }
}
