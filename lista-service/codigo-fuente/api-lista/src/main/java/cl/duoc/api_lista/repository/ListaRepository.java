package cl.duoc.api_lista.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.api_lista.model.Lista;

@Repository

public interface ListaRepository extends JpaRepository<Lista, Long> {
    
    List <Lista> findByUsuarioId(Long usuarioId);
    
}
