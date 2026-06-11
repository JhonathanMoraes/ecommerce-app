package com.pp.ecommerce_app.controllers;

import com.pp.ecommerce_app.dtos.PedidoDTO;
import com.pp.ecommerce_app.dtos.UsuarioDTO;
import com.pp.ecommerce_app.services.PedidoService;
import com.pp.ecommerce_app.services.ProdutoService;
import com.pp.ecommerce_app.commands.CriarPedidoCommand;
import com.pp.ecommerce_app.commands.AtualizarStatusPedidoCommand;
import com.pp.ecommerce_app.commands.CancelarPedidoCommand;
import com.pp.ecommerce_app.commands.AvaliarPedidoCommand;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public String listarPedidos(Model modelo, HttpSession sessao) {
        UsuarioDTO usuarioLogado = (UsuarioDTO) sessao.getAttribute("usuarioLogado");
        modelo.addAttribute("pedidos", pedidoService.listarPorUsuario(usuarioLogado.getId()));
        return "lista_pedidos";
    }

    @GetMapping("/{id}")
    public String exibirDetalhe(@PathVariable("id") int id, Model modelo) {
        modelo.addAttribute("pedido", pedidoService.buscarPorId(id));
        return "detalhes_pedido";
    }

    @GetMapping("/novo")
    public String exibirFormularioPedido(Model modelo, HttpSession sessao) {
        UsuarioDTO usuarioLogado = (UsuarioDTO) sessao.getAttribute("usuarioLogado");
        PedidoDTO dto = new PedidoDTO();
        dto.setUsuario(usuarioLogado.getId());
        dto.setEndereco(usuarioLogado.getEndereco());
        modelo.addAttribute("pedidoDTO", dto);
        modelo.addAttribute("produtos", produtoService.listarAtivos());
        return "pedidos/formulario";
    }

    @PostMapping("/salvar")
    public String salvarPedido(@ModelAttribute("pedidoDTO") PedidoDTO pedidoDTO,
                               HttpSession sessao,
                               RedirectAttributes atributos) {
        try {
            new CriarPedidoCommand(pedidoService, pedidoDTO).executar();
            atributos.addFlashAttribute("sucesso", "Pedido realizado com sucesso!");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
            return "redirect:/pedidos/novo";
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/status/{id}")
    public String atualizarStatus(@PathVariable("id") int id,
                                  @RequestParam("status") String status,
                                  RedirectAttributes atributos) {
        new AtualizarStatusPedidoCommand(pedidoService, id, status).executar();
        atributos.addFlashAttribute("sucesso", "Status do pedido atualizado!");
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/cancelar/{id}")
    public String cancelarPedido(@PathVariable("id") int id, RedirectAttributes atributos) {
        new CancelarPedidoCommand(pedidoService, id).executar();
        atributos.addFlashAttribute("sucesso", "Pedido cancelado com sucesso!");
        return "redirect:/pedidos";
    }

    @PostMapping("/avaliar/{id}")
    public String avaliarPedido(@PathVariable("id") int id,
                                @RequestParam("nota") int nota,
                                RedirectAttributes atributos) {
        try {
            new AvaliarPedidoCommand(pedidoService, id, nota).executar();
            atributos.addFlashAttribute("sucesso", "Avaliação do pedido registrada!");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/pedidos/" + id;
    }
}
