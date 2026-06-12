package cl.duoc.api_contenido.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.api_contenido.model.Contenido;


@Repository

public interface ContenidoRepository extends JpaRepository<Contenido,Long>{

    
    boolean existsByTituloIgnoreCase(String titulo);
    
    List<Contenido> findByTituloContainingIgnoreCase(String titulo);

    
   
 
    


    
}