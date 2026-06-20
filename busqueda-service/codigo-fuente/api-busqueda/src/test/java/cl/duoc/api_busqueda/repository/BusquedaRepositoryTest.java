package cl.duoc.api_busqueda.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.api_busqueda.model.Busqueda;

@DataJpaTest


public class BusquedaRepositoryTest {

    @Autowired 
    private BusquedaRepository busquedaRepository;
    @BeforeEach
    void setUp() {
        busquedaRepository.deleteAll(); 
    }

    @Test
    @DisplayName("save - debe persistir la busqueda y asignar un ID generado automáticamente")
        void debePersistirBusquedaYAsignarIdGenerado() {
        // Given
        Busqueda b = new Busqueda(null, 1L,"The Dark Knight");

        // When
        Busqueda guardado = busquedaRepository.save(b);

        // Then

        assertNotNull(guardado.getId());

        assertTrue(guardado.getId() > 0);
                                    //esperado, actual
        assertEquals(1L, guardado.getContenidoId());
        assertEquals("The Dark Knight", guardado.getTitulo());
        
     
    }



    @Test
    @DisplayName("findAll - debe retornar todas las busquedas guardados en la BD")
    void debeRetornarTodasLasBusquedaGuardados() {
        // Given
        busquedaRepository.save(new Busqueda(
            null, 
            2L,
            "The Last of Us"

        ));

        busquedaRepository.save(new Busqueda(
            null, 
            3L,
            "The Matrix"

        ));

        // When
        List<Busqueda> contenido = busquedaRepository.findAll();

        // Then
        assertNotNull(contenido);
        assertEquals(2, contenido.size());
    }

    @Test
    @DisplayName("findById - debe retornar la busqueda correcto cuando el ID existe")
    void debeEncontrarBusquedaPorIdExistente() {
        // Given
        Busqueda guardado = busquedaRepository.save( new Busqueda(
            null, 
            1L,
            "Avatar"

        ));

        // When
        Optional<Busqueda> resultado = busquedaRepository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());   
        assertEquals(1L, resultado.get().getContenidoId());    
         assertEquals("Avatar",resultado.get().getTitulo());
    

    }

    @Test
    @DisplayName("deleteById - debe eliminar la busqueda de la base de datos")
    void debeEliminarBusquedaPorId() {
        // Given
        Busqueda guardado = busquedaRepository.save( new Busqueda(
            null, 
            1L,
            "El Viaje de Chihiro"

        ));
        Long id = guardado.getId();

        // When
        busquedaRepository.deleteById(id);

        // Then
        assertFalse(busquedaRepository.findById(id).isPresent());
    }





}
