package cl.duoc.api_busqueda.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BusquedaTest {

    @Test
    @DisplayName("Constructor vacío - debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNoNula() {
        Busqueda busqueda = new Busqueda();
        assertNotNull(busqueda);
    }  
    
    @Test
    @DisplayName("Constructor completo - debe asignar todos los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos() {
        // Given & When 
        Busqueda busqueda = new Busqueda(1L, 100L, "Inception");
        
        // Then
        assertEquals(1L, busqueda.getId());
        assertEquals(100L, busqueda.getContenidoId());
        assertEquals("Inception", busqueda.getTitulo());
    }

    @Test
    @DisplayName("Setters y Getters - debe permitir modificar y recuperar cada campo individualmente")
    void gettersYSettersDebenAsignarYRecuperarCampos() {
        // Given
        Busqueda busqueda = new Busqueda();

        // When
        busqueda.setId(2L);
        busqueda.setContenidoId(200L);
        busqueda.setTitulo("Interstellar");

        // Then
        assertEquals(2L, busqueda.getId());
        assertEquals(200L, busqueda.getContenidoId());
        assertEquals("Interstellar", busqueda.getTitulo());
    }
    
    @Test
    @DisplayName("Equals y HashCode - dos búsquedas con idéntica data deben ser iguales")
    void dosBusquedasConMismosDatosDebenSerIguales() {
        // Given
        Busqueda b1 = new Busqueda(1L, 300L, "Oppenheimer");
        Busqueda b2 = new Busqueda(1L, 300L, "Oppenheimer");
        
        // Then
        assertEquals(b1, b2); 
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    @DisplayName("ToString - debe contener los datos principales del objeto en formato de texto")
    void toStringDebeContenerDatosClave() {
        Busqueda busqueda = new Busqueda(1L,99L, "Interstella");

        String texto = busqueda.toString();

        
        assertNotNull(texto);
        assertTrue(texto.contains("1"));
        assertTrue(texto.contains("99"));
        assertTrue(texto.contains("Interstella"));
 
    }



    
}
