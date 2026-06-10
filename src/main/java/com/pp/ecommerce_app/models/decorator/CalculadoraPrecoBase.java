package com.pp.ecommerce_app.models.decorator;

import com.pp.ecommerce_app.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class CalculadoraPrecoBase implements CalculadoraPreco {

    @Override
    public double calcular(Pedido pedido) {
        if (pedido.getProduto() != null) {
            return (pedido.getProduto().getPreco() * pedido.getQuantidade()) + pedido.getFrete();
        }
        return 0.0;
    }
}