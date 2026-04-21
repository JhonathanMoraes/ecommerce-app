package com.pp.ecommerce_app.repositories;

import com.pp.ecommerce_app.models.Produto;
import com.pp.ecommerce_app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findAllByAtivo(boolean ativo);

    List<Produto> findAllByUsuarioAndAtivo(Usuario usuario, boolean ativo);
}
