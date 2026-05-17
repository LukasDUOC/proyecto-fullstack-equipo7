package cl.duoc.api_ratings.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.api_ratings.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
     boolean existsByIdUsuarioAndIdContenido(
            Long idUsuario,
            Long idContenido);
    
}
