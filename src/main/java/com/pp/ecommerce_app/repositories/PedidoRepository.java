package com.pp.ecommerce_app.repositories;

import com.pp.ecommerce_app.models.Pedido;
import com.pp.ecommerce_app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findAllByUsuario(Usuario usuario);

    List<Pedido> findAllByUsuarioAndStatus(Usuario usuario, String status);

    List<Pedido> findAllByUsuarioAndAtivo(Usuario usuario, boolean ativo);

    @Query("SELECT AVG(p.nota) FROM Pedido p WHERE p.produto.id = :produtoId AND p.nota IS NOT NULL")
    Double mediaNotasPorProduto(@Param("produtoId") int produtoId);
}
