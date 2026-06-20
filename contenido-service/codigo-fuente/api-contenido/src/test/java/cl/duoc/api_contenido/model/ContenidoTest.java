package cl.duoc.api_contenido.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



public class ContenidoTest {
    @Test
    @DisplayName("Constructor vacio, debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNula(){
        Contenido contenido = new Contenido();
        assertNotNull(contenido);
    }  
    
    
    @Test
    @DisplayName("Constructor completo, debe asiganr los compos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos(){
        Contenido contenido = new Contenido(1L,"Interstella","Ciencia Ficción","la Tierra se está volviendo inhabitable", "169 minutos","pelicula","HBO Max",LocalDate.parse("2014-11-07"));
        assertEquals(1L, contenido.getId());
        assertEquals("Interstella", contenido.getTitulo());
        assertEquals("Ciencia Ficción", contenido.getGenero());
        assertEquals("la Tierra se está volviendo inhabitable", contenido.getSinopsis());
        assertEquals("169 minutos", contenido.getDuracion());
        assertEquals("pelicula", contenido.getTipo());
        assertEquals("HBO Max", contenido.getVisualizar());
        assertEquals(LocalDate.parse("2014-11-07"), contenido.getFechaLan());
    }



    @Test
    @DisplayName("Setters y Getters - debe permitir modificar y recuperar cada campo individualmente")
    void gettersYSettersDebenAsignarYRecuperarCampos() {
        Contenido contenido = new Contenido();

        contenido.setId(2L);
        contenido.setTitulo("Inception");
        contenido.setGenero("Ciencia Ficción");
        contenido.setSinopsis("Un ladrón que roba secretos a través del subconsciente");
        contenido.setDuracion("148 minutos");
        contenido.setTipo("pelicula");
        contenido.setVisualizar("Netflix");
        contenido.setFechaLan(LocalDate.parse("2010-07-28"));

        assertEquals(2L, contenido.getId());
        assertEquals("Inception", contenido.getTitulo());
        assertEquals("Ciencia Ficción", contenido.getGenero());
        assertEquals("Un ladrón que roba secretos a través del subconsciente", contenido.getSinopsis());
        assertEquals("148 minutos", contenido.getDuracion());
        assertEquals("pelicula", contenido.getTipo());
        assertEquals("Netflix", contenido.getVisualizar());
        assertEquals(LocalDate.parse("2010-07-28"), contenido.getFechaLan());
    }

    
    @Test
    @DisplayName("Equals y HashCode - dos contenidos con los mismos datos deben ser iguales")
    void dosContenidoConMismosDatosDebenSerIguales() {
        
        Contenido contenido1 = new Contenido(1L, "Interstella", "Ciencia Ficción", "la Tierra se está volviendo inhabitable", "169 minutos", "pelicula", "HBO Max", LocalDate.parse("2014-11-07"));
        Contenido contenido2 = new Contenido(1L, "Interstella", "Ciencia Ficción", "la Tierra se está volviendo inhabitable", "169 minutos", "pelicula", "HBO Max", LocalDate.parse("2014-11-07"));
        
        
        assertEquals(contenido1.hashCode(), contenido2.hashCode());
    }


    @Test
    @DisplayName("ToString - debe contener los datos principales del objeto en formato de texto")
    void toStringDebeContenerDatosClave() {
        Contenido contenido = new Contenido(1L, "Interstella", "Ciencia Ficción", "la Tierra se está volviendo inhabitable", "169 minutos", "pelicula", "HBO Max", LocalDate.parse("2014-11-07"));

        String texto = contenido.toString();

        
        assertNotNull(texto);
        assertTrue(texto.contains("1"));
        assertTrue(texto.contains("Interstella"));
        assertTrue(texto.contains("Ciencia Ficción"));
        assertTrue(texto.contains("pelicula"));
        assertTrue(texto.contains("HBO Max"));
    }

    
}
