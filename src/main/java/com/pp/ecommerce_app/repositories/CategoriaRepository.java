package com.pp.ecommerce_app.repositories;

import com.pp.ecommerce_app.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByAtivo(boolean ativo);

    boolean existsByNome(String nome);
}
