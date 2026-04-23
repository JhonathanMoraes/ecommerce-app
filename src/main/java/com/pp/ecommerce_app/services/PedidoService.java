package com.pp.ecommerce_app.services;

import com.pp.ecommerce_app.dtos.PedidoDTO;
import com.pp.ecommerce_app.models.Pedido;
import com.pp.ecommerce_app.models.Produto;
import com.pp.ecommerce_app.models.Usuario;
import com.pp.ecommerce_app.models.pagamento.Pagamento;
import com.pp.ecommerce_app.models.pagamento.PagamentoCredito;
import com.pp.ecommerce_app.models.pagamento.PagamentoDebito;
import com.pp.ecommerce_app.models.pagamento.PagamentoPix;
import com.pp.ecommerce_app.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PagamentoPix pagamentoPix;

    @Autowired
    private PagamentoDebito pagamentoDebito;

    @Autowired
    private PagamentoCredito pagamentoCredito;

    private PedidoDTO paraDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO(
            pedido.getId(),
            pedido.getUsuario().getId(),
            pedido.getProduto().getId(),
            pedido.getQuantidade(),
            pedido.getTipoDePagamento(),
            pedido.getEndereco(),
            pedido.getFrete()
        );
        dto.setStatus(pedido.getStatus());
        dto.setTotal(pedido.getTotal());
        dto.setAtivo(pedido.isAtivo());
        dto.setNomeProduto(pedido.getProduto().getNome());
        dto.setNota(pedido.getNota());
        return dto;
    }

    private Pagamento resolverPagamento(String tipoDePagamento) {
        Pagamento pagamento = null;
        
        switch (tipoDePagamento.toUpperCase()) {
            case "PIX" -> pagamento = pagamentoPix;
            case "DEBITO" -> pagamento = pagamentoDebito;
            case "CREDITO" -> pagamento = pagamentoCredito;
        }
        return pagamento;
    }

    public PedidoDTO criarPedido(PedidoDTO dto) {
        Usuario usuario = usuarioService.buscarEntidadePorId(dto.getUsuario());
        Produto produto  = produtoService.buscarEntidadePorId(dto.getProduto());

        if (produto.getQuantidade() < dto.getQuantidade()) {
            throw new IllegalArgumentException("Quantidade indisponível em estoque.");
        }
        produtoService.atualizarQuantidadeEmEstoque(produto.getId(), dto.getQuantidade());

        Pedido pedido = new Pedido(
            usuario,
            produto,
            dto.getQuantidade(),
            dto.getTipoDePagamento(),
            dto.getEndereco(),
            dto.getFrete()
        );

        pedido.calcularTotal();

        Pagamento pagamento = resolverPagamento(dto.getTipoDePagamento());
        pedido.setTotal(pagamento.aplicarDesconto(pedido));

        Pedido salvo = pedidoRepository.save(pedido);
        salvo.setAtivo(true);
        return paraDTO(salvo);
    }

    public PedidoDTO buscarPorId(int id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: id=" + id));
        return paraDTO(pedido);
    }

    public List<PedidoDTO> listarPorUsuario(int usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return pedidoRepository.findAllByUsuario(usuario)
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> listarPorUsuarioEStatus(int usuarioId, String status) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return pedidoRepository.findAllByUsuarioAndStatus(usuario, status)
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public void atualizarStatus(int id, String status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: id=" + id));
        pedido.setStatus(status);
        pedidoRepository.save(pedido);
    }

    public void cancelar(int id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: id=" + id));
        pedido.setStatus("CANCELADO");
        pedido.setAtivo(false);
        pedidoRepository.save(pedido);
    }

    public void avaliarPedido(int id, int nota) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: id=" + id));
        
        if (!"ENVIADO".equals(pedido.getStatus())) {
            throw new IllegalArgumentException("Apenas pedidos enviados podem ser avaliados.");
        }
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("A nota deve ser entre 0 e 10.");
        }
        
        pedido.setNota(nota);
        pedidoRepository.save(pedido);
        
        Double media = pedidoRepository.mediaNotasPorProduto(pedido.getProduto().getId());
        if (media != null) {
            produtoService.avaliar(pedido.getProduto().getId(), (int) Math.round(media));
        }
    }
}
