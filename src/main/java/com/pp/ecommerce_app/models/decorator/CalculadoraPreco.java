package com.pp.ecommerce_app.models.decorator;

import com.pp.ecommerce_app.models.Pedido;

public interface CalculadoraPreco {
    double calcular(Pedido pedido);
}