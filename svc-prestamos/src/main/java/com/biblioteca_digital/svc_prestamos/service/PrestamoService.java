package com.biblioteca_digital.svc_prestamos.service;

import com.biblioteca_digital.svc_prestamos.dto.PrestamoRequest;
import com.biblioteca_digital.svc_prestamos.model.Prestamo;
import com.biblioteca_digital.svc_prestamos.repository.PrestamoRepository;
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
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String defaultEstado = "ACTIVO";

    public Map<String, Object> crearPrestamo(PrestamoRequest prestamoRequest){
        Prestamo prestamo = new Prestamo();
        String id = "PRE-" + UUID.randomUUID().toString();
        prestamo.setId(id);
        prestamo.setIsbn(prestamoRequest.getIsbn());
        prestamo.setMiembroId(prestamoRequest.getMiembroId());
        prestamo.setFechaPrestamo(new Date().toString());
        prestamo.setFechaDevolucionEstimada(prestamoRequest.getFechaDevolucionEstimada());
        prestamo.setFechaDevolucionReal(prestamoRequest.getFechaDevolucionReal());
        prestamo.setEstado(defaultEstado);
        prestamoRepository.save(prestamo);
        stringRedisTemplate.delete("prestamo:all");
        return Map.of("mensaje", "Prestamo creado correctamente", "identifciador", prestamo.getId());
    }

    public Map<String, Object> obtenerPrestamo(String id){
        long tiempoTranscurrido = 0, inicioTiempo = 0, finTiempo= 0;
        String cacheKey = "prestamo:"+id;
        inicioTiempo = System.currentTimeMillis();
        String cached = stringRedisTemplate.opsForValue().get(cacheKey);
        finTiempo = System.currentTimeMillis();
        if(cached != null){
            try{
                Map<String, Object> prestamoCache = objectMapper.readValue(cached, new TypeReference<Map<String, Object>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
                tiempoTranscurrido = finTiempo - inicioTiempo;
                return Map.of("Fuente", "cache redis ","tiempo en milisegundos", tiempoTranscurrido, "datos", prestamoCache);
            } catch (Exception ex){
                System.err.println("error: "+ ex.getMessage());
                stringRedisTemplate.delete(cacheKey);
            }
        }
        inicioTiempo = System.currentTimeMillis();
        Optional<Prestamo> prestamoDB = prestamoRepository.findById(id);
        finTiempo = System.currentTimeMillis();
        if(prestamoDB.isEmpty()){
            return Map.of("error", "Prestamo no encontrado con el identificador: "+ id);
        }
        Prestamo prestamoGet = prestamoDB.get();
        try{
            String jsonPrestamo = objectMapper.writeValueAsString(prestamoGet);
            stringRedisTemplate.opsForValue().set(cacheKey, jsonPrestamo, 60, TimeUnit.SECONDS);
        }catch (Exception ex){
            System.err.println("Error: "+ ex.getMessage());
        }
        tiempoTranscurrido = finTiempo - inicioTiempo;
        return Map.of("Fuente", "base de datos", "tiempo en milisegundos", tiempoTranscurrido,"datos", prestamoGet);
    }
}
