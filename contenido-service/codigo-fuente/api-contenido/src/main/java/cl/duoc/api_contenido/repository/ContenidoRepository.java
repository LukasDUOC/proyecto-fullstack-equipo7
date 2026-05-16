package cl.duoc.api_contenido.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.api_contenido.model.Contenido;
import jakarta.transaction.Transactional;

@Repository

public interface ContenidoRepository extends JpaRepository<Contenido,Long>{

    
    boolean existsByTitulo(String titulo);

    @Transactional
    Optional<Contenido> findByTitulo(String titulo);

    @Transactional
    void deleteByTituloIgnoreCase(String titulo);

    
    List<Contenido> findByTituloContainingIgnoreCase(String titulo);

    
    List<Contenido> findByGeneroIgnoreCase(String genero);
 
    


    
}