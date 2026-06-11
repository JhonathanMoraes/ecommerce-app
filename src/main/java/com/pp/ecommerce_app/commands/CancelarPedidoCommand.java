package com.pp.ecommerce_app.commands;

import com.pp.ecommerce_app.services.PedidoService;

public class CancelarPedidoCommand implements ICommand<Void> {

    private final PedidoService pedidoService;
    private final int id;

    public CancelarPedidoCommand(PedidoService pedidoService, int id) {
        this.pedidoService = pedidoService;
        this.id = id;
    }

    @Override
    public void executar() {
        pedidoService.cancelar(id);
    }
}
