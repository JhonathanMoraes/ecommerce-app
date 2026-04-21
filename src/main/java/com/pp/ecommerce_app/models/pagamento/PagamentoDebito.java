package com.pp.ecommerce_app.models.pagamento;

import com.pp.ecommerce_app.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PagamentoDebito implements Pagamento {

    private static final double DESCONTO_DEBITO = 0.05;

    @Override
    public double aplicarDesconto(Pedido pedido) {
        return pedido.getTotal() * (1 - DESCONTO_DEBITO);
    }
}
