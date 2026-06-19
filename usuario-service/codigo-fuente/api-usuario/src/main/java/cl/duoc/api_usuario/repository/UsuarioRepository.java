package cl.duoc.api_usuario.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.api_usuario.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    @Transactional
    @Modifying
    void deleteByEmail(String email);

    Optional<Usuario> findByEmail(String email);
}
