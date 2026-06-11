package com.pp.ecommerce_app.commands;

import com.pp.ecommerce_app.dtos.PedidoDTO;
import com.pp.ecommerce_app.services.PedidoService;

public class CriarPedidoCommand implements ICommand<PedidoDTO> {

    private final PedidoService pedidoService;
    private final PedidoDTO dto;

    public CriarPedidoCommand(PedidoService pedidoService, PedidoDTO dto) {
        this.pedidoService = pedidoService;
        this.dto = dto;
    }

    @Override
    public PedidoDTO executar() {
        return pedidoService.criarPedido(dto);
    }
}
