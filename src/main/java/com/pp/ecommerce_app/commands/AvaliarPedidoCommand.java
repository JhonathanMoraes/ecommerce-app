package com.pp.ecommerce_app.commands;

import com.pp.ecommerce_app.services.PedidoService;

public class AvaliarPedidoCommand implements ICommand<Void> {

    private final PedidoService pedidoService;
    private final int id;
    private final int nota;

    public AvaliarPedidoCommand(PedidoService pedidoService, int id, int nota) {
        this.pedidoService = pedidoService;
        this.id = id;
        this.nota = nota;
    }

    @Override
    public Void executar() {
        pedidoService.avaliarPedido(id, nota);
        return null;
    }
}
