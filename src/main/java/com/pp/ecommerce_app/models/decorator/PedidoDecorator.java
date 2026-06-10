package com.pp.ecommerce_app.models.decorator;

import com.pp.ecommerce_app.models.Pedido;

public abstract class PedidoDecorator implements CalculadoraPreco {

    protected CalculadoraPreco calculadoraPreco;

    public PedidoDecorator(CalculadoraPreco calculadoraPreco) {
        this.calculadoraPreco = calculadoraPreco;
    }

    @Override
    public double calcular(Pedido pedido) {
        return calculadoraPreco.calcular(pedido);
    }
}