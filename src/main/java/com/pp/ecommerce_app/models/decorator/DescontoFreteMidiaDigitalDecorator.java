package com.pp.ecommerce_app.models.decorator;

import com.pp.ecommerce_app.models.Categoria;
import com.pp.ecommerce_app.models.Pedido;

public class DescontoFreteMidiaDigitalDecorator extends PedidoDecorator {

    public DescontoFreteMidiaDigitalDecorator(CalculadoraPreco calculadoraPreco) {
        super(calculadoraPreco);
    }

    @Override
    public double calcular(Pedido pedido) {
        double precoCalculado = super.calcular(pedido);

        if (pedido.getProduto() != null && pedido.getProduto().getCategorias() != null) {
            for (Categoria categoria : pedido.getProduto().getCategorias()) {
                if ("Mídia Digital".equalsIgnoreCase(categoria.getNome()) || "Midia Digital".equalsIgnoreCase(categoria.getNome())) {
                    precoCalculado -= pedido.getFrete();
                    // Garante que não desconte o frete mais de uma vez se houver categorias duplicadas,
                    // embora não devesse ocorrer, podemos usar break.
                    break;
                }
            }
        }
        
        return precoCalculado;
    }
}
