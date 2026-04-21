package com.pp.ecommerce_app.models.pagamento;

import com.pp.ecommerce_app.models.Pedido;

public interface Pagamento {
    double aplicarDesconto(Pedido pedido);
}
