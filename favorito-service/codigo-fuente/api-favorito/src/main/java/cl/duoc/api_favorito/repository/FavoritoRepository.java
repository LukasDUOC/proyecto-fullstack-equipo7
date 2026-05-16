package cl.duoc.api_favorito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import cl.duoc.api_favorito.model.Favorito;


@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    
}
