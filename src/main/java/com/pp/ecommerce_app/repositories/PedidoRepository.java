package com.pp.ecommerce_app.repositories;

import com.pp.ecommerce_app.models.Pedido;
import com.pp.ecommerce_app.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findAllByUsuario(Usuario usuario);

    List<Pedido> findAllByUsuarioAndStatus(Usuario usuario, String status);

    List<Pedido> findAllByUsuarioAndAtivo(Usuario usuario, boolean ativo);
}
