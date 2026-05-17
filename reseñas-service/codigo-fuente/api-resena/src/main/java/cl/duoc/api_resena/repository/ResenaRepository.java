package cl.duoc.api_resena.repository;
import cl.duoc.api_resena.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByUsuarioId(Long usuarioId);
    List<Resena> findByContenidoId(Long contenidoId);
    
}

