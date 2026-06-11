package com.pp.ecommerce_app.models.decorator;

import com.pp.ecommerce_app.models.Categoria;
import com.pp.ecommerce_app.models.Pedido;

public class TaxaGamerDecorator extends PedidoDecorator {

    public TaxaGamerDecorator(CalculadoraPreco calculadoraPreco) {
        super(calculadoraPreco);
    }

    @Override
    public double calcular(Pedido pedido) {
        double precoCalculado = super.calcular(pedido);

        if (pedido.getProduto() != null && pedido.getProduto().getCategorias() != null) {
            for (Categoria categoria : pedido.getProduto().getCategorias()) {
                if ("Gamer".equalsIgnoreCase(categoria.getNome())) {
                    precoCalculado += pedido.getProduto().getPreco() * 0.05 * pedido.getQuantidade();
                }
            }
        }
        
        return precoCalculado;
    }
}
