package cl.duoc.api_busqueda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.api_busqueda.dto.ContenidoDTO;
import cl.duoc.api_busqueda.model.Busqueda;

@Repository
public interface BusquedaRepository extends JpaRepository <Busqueda,Long> {


    List<Busqueda> findByTituloContainingIgnoreCase(String titulo);
    List<ContenidoDTO> findByTituloContaining(String titulo);
    
    
}
