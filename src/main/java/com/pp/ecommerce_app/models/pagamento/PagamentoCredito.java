package com.pp.ecommerce_app.models.pagamento;

import com.pp.ecommerce_app.models.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PagamentoCredito implements Pagamento {

    @Override
    public double aplicarDesconto(Pedido pedido) {
        return pedido.getTotal();
    }
}
