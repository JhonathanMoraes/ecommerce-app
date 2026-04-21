package com.pp.ecommerce_app.repositories;

import com.pp.ecommerce_app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    List<Usuario> findAllByAtivo(boolean ativo);

    boolean existsByEmail(String email);
}
