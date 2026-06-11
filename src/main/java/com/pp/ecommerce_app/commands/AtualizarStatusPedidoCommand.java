package com.pp.ecommerce_app.commands;

import com.pp.ecommerce_app.services.PedidoService;

public class AtualizarStatusPedidoCommand implements ICommand<Void> {

    private final PedidoService pedidoService;
    private final int id;
    private final String status;

    public AtualizarStatusPedidoCommand(PedidoService pedidoService, int id, String status) {
        this.pedidoService = pedidoService;
        this.id = id;
        this.status = status;
    }

    @Override
    public void executar() {
        pedidoService.atualizarStatus(id, status);
    }
}
