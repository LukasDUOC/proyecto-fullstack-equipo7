package cl.duoc.api_contenido.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.duoc.api_contenido.model.Contenido;

@DataJpaTest

public class ContenidoRepositoryTest {

    @Autowired 
    private ContenidoRepository contenidoRepository;
    @BeforeEach
    void setUp() {
        contenidoRepository.deleteAll(); 
    }

    @Test
    @DisplayName("save - debe persistir el contenido y asignar un ID generado automáticamente")
        void debePersistirContenidoYAsignarIdGenerado() {
        // Given
        Contenido contenido = new Contenido(null, "The Dark Knight", "Acción", "Batman se enfrenta al Guasón, un cerebro criminal que desata el caos en Ciudad Gótica", "152 minutos", "pelicula", "HBO Max", LocalDate.parse("2008-07-17"));

        // When
        Contenido guardado = contenidoRepository.save(contenido);

        // Then

        assertNotNull(guardado.getId());

        assertTrue(guardado.getId() > 0);
                                    //esperado, actual
        assertEquals("The Dark Knight", guardado.getTitulo());
        assertEquals("Acción", guardado.getGenero());
        assertEquals("Batman se enfrenta al Guasón, un cerebro criminal que desata el caos en Ciudad Gótica", guardado.getSinopsis());
        assertEquals("152 minutos", guardado.getDuracion());
        assertEquals("pelicula", guardado.getTipo());
        assertEquals("HBO Max", guardado.getVisualizar());
        assertEquals(LocalDate.parse("2008-07-17"), guardado.getFechaLan());
    }



    @Test
    @DisplayName("findAll - debe retornar todos los contenidos guardados en la BD")
    void debeRetornarTodosLosProductosGuardados() {
        // Given
        contenidoRepository.save(new Contenido(
            null, 
            "The Last of Us", 
            "Drama / Ciencia Ficción", 
            "Un contrabandista debe escoltar a una adolescente a través de un Estados Unidos postapocalíptico", 
            "9 episodios", 
            "serie", 
            "HBO Max", 
            LocalDate.parse("2023-01-15")
        ));

        contenidoRepository.save(new Contenido(
            null, 
            "The Matrix", 
            "Ciencia Ficción", 
            "Un programador de computación descubre que el mundo en el que vive es una simulación controlada por máquinas", 
            "136 minutos", 
            "pelicula", 
            "Netflix", 
            LocalDate.parse("1999-06-10")
        ));

        // When
        List<Contenido> contenido = contenidoRepository.findAll();

        // Then
        assertNotNull(contenido);
        assertEquals(2, contenido.size());
    }

    @Test
    @DisplayName("findById - debe retornar el contenido correcto cuando el ID existe")
    void debeEncontrarContenidoPorIdExistente() {
        // Given
        Contenido guardado = contenidoRepository.save( new Contenido(
            null, 
            "Avatar", 
            "Ciencia Ficción / Aventura", 
            "Un exmarino parapléjico es enviado a un planeta llamado Pandora en una misión única", 
            "162 minutos", 
            "pelicula", 
            "Disney+", 
            LocalDate.parse("2009-12-18")
        ));

        // When
        Optional<Contenido> resultado = contenidoRepository.findById(guardado.getId());

        // Then
        assertTrue(resultado.isPresent());       
         assertEquals("Avatar",resultado.get().getTitulo());
        assertEquals("Ciencia Ficción / Aventura", resultado.get().getGenero());
        assertEquals("Un exmarino parapléjico es enviado a un planeta llamado Pandora en una misión única", resultado.get().getSinopsis());
        assertEquals("162 minutos", resultado.get().getDuracion());
        assertEquals("pelicula", resultado.get().getTipo());
        assertEquals("Disney+", resultado.get().getVisualizar());
        assertEquals(LocalDate.parse("2009-12-18"), resultado.get().getFechaLan());
    }

    @Test
    @DisplayName("findById - debe retornar Optional vacío cuando el ID no existe")
    void debeRetornarOptionalVacioCuandoIdNoExiste() {
        // When
        Optional<Contenido> resultado = contenidoRepository.findById(999L);

        // Then
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("deleteById - debe eliminar el producto de la base de datos")
    void debeEliminarProductoPorId() {
        // Given
        Contenido guardado = contenidoRepository.save( new Contenido(
            null, 
            "El Viaje de Chihiro", 
            "Animación / Fantasía", 
            "Una niña de diez años se adentra en un mundo gobernado por dioses, brujas y espíritus", 
            "125 minutos", 
            "pelicula", 
            "Crunchyroll", 
            LocalDate.parse("2001-07-20")
        ));
        Long id = guardado.getId();

        // When
        contenidoRepository.deleteById(id);

        // Then
        assertFalse(contenidoRepository.findById(id).isPresent());
    }





}
