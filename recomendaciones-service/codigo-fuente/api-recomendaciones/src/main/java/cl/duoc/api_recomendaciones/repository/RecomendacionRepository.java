package cl.duoc.api_recomendaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.api_recomendaciones.model.Recomendacion;

@Repository
public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {

    
} 
