package cl.duoc.api_busqueda.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BusquedaTest {

    @Test
    @DisplayName("Constructor vacio, debe crear una instancia no nula")
    void constructorVacioDebeCrearInstanciaNula(){
        Busqueda busqueda = new Busqueda();
        assertNotNull(busqueda);
    } 

    @Test
    @DisplayName("Constructor completo, debe asiganr los campos correctamente")
    void constructorCompletoDebeAsignarTodosLosCampos(){
        Busqueda b = new Busqueda(1L,99L,"Interstellar");
        assertEquals(1L, b.getId());
        assertEquals(99L, b.getContenidoId());
        assertEquals("Interstellar", b.getTitulo());
  
    }


    @Test
    @DisplayName("Setters - debe permitir modificar cada campo individualmente")
    void gettersYSettersDebenAsignarYRecuperarCampos() {
        Busqueda b = new Busqueda();

        
        b.setId(2L);
        b.setContenidoId(99L);
        b.setTitulo("Inception");


 
        assertEquals(2L, b.getId());
        assertEquals(99L, b.getContenidoId());
        assertEquals("Inception", b.getTitulo());
      
    }

    
    @Test
    @DisplayName("Equals y HashCode - dos contenidos con los mismos datos deben ser iguales")
    void dosContenidoConMismosDatosDebenSerIguales() {
        
        Busqueda busqueda1 = new Busqueda(1L, 99L,"Interstella");
        Busqueda busqueda2 = new Busqueda(1L, 99L,"Interstella");
        
        
        assertEquals(busqueda1.hashCode(), busqueda2.hashCode());
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
