package com.pp.ecommerce_app.models.decorator;

import com.pp.ecommerce_app.models.Categoria;
import com.pp.ecommerce_app.models.Pedido;

public class DescontoLivrosDecorator extends PedidoDecorator {

    public DescontoLivrosDecorator(CalculadoraPreco calculadoraPreco) {
        super(calculadoraPreco);
    }

    @Override
    public double calcular(Pedido pedido) {
        double precoCalculado = super.calcular(pedido);

        if (pedido.getProduto() != null && pedido.getProduto().getCategorias() != null) {
            for (Categoria categoria : pedido.getProduto().getCategorias()) {
                if ("Livros".equalsIgnoreCase(categoria.getNome())) {
                    precoCalculado -= pedido.getProduto().getPreco() * 0.10 * pedido.getQuantidade();
                }
            }
        }
        
        return precoCalculado;
    }
}
