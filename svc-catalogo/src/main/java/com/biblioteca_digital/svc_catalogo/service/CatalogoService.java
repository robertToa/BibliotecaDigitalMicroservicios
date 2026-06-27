package com.biblioteca_digital.svc_catalogo.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.biblioteca_digital.svc_catalogo.dto.CatalogoRequest;
import com.biblioteca_digital.svc_catalogo.model.Catalogo;
import com.biblioteca_digital.svc_catalogo.repository.CatalogoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CatalogoService {

    private static final boolean defaultDisponible = true;

    @Autowired
    private CatalogoRepository catalogoRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, Object> crearCatalogo(CatalogoRequest catalogoRequest){
        Catalogo catalogo = new Catalogo();
        catalogo.setIsbn(catalogoRequest.getIsbn());
        catalogo.setTitulo(catalogoRequest.getTitulo());
        catalogo.setAutor(catalogoRequest.getAutor());
        catalogo.setGenero(catalogoRequest.getGenero());
        catalogo.setAnioPublicacion(catalogoRequest.getAnioPublicacion());
        catalogo.setDisponible(defaultDisponible);
        catalogo.setCopiasTotales(catalogoRequest.getCopiasTotales());
        catalogo.setCopiasDisponibles(catalogoRequest.getCopiasDisponibles());
        catalogoRepository.save(catalogo);
        stringRedisTemplate.delete("catalogo:all");
        return Map.of("mensaje", "Catalogo creado correctamente", "identifciador", catalogo.getId());
    }

    public Map<String, Object> obtenerLibroPorIsbn(String isbn){
        long tiempoTranscurrido = 0, inicioTiempo = 0, finTiempo= 0;
        String cacheKey = "catalogo:isbn:"+isbn;
        inicioTiempo = System.currentTimeMillis();
        String cached = stringRedisTemplate.opsForValue().get(cacheKey);
        finTiempo = System.currentTimeMillis();
        if(cached != null){
            try{
                Map<String, Object> catalogoCache = objectMapper.readValue(cached, new TypeReference<Map<String, Object>>() {
                    @Override
                    public Type getType() {
                        return super.getType();
                    }
                });
                tiempoTranscurrido = finTiempo - inicioTiempo;
                return Map.of("Fuente", "cache redis ","tiempo en milisegundos", tiempoTranscurrido, "datos", catalogoCache);
            } catch (Exception ex){
                System.err.println("error: "+ ex.getMessage());
                stringRedisTemplate.delete(cacheKey);
            }
        }
        inicioTiempo = System.currentTimeMillis();
        Optional<Catalogo> catalogoDB = catalogoRepository.findByIsbn(isbn);
        finTiempo = System.currentTimeMillis();
        if(catalogoDB.isEmpty()){
            return Map.of("error", "Catalogo no encontrado con el isbn: "+ isbn);
        }
        Catalogo catalogoGet = catalogoDB.get();
        try{
            String jsonCatalogo = objectMapper.writeValueAsString(catalogoGet);
            stringRedisTemplate.opsForValue().set(cacheKey, jsonCatalogo, 60, TimeUnit.SECONDS);
        }catch (Exception ex){
            System.err.println("Error: "+ ex.getMessage());
        }
        tiempoTranscurrido = finTiempo - inicioTiempo;
        return Map.of("Fuente", "base de datos", "tiempo en milisegundos", tiempoTranscurrido,"datos", catalogoGet);
    }

}
