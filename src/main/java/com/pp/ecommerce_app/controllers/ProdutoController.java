package com.pp.ecommerce_app.controllers;

import com.pp.ecommerce_app.dtos.ProdutoDTO;
import com.pp.ecommerce_app.dtos.UsuarioDTO;
import com.pp.ecommerce_app.services.ProdutoService;
import com.pp.ecommerce_app.services.CategoriaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public String listarProdutos(Model modelo) {
        modelo.addAttribute("produtos", produtoService.listarAtivos());
        return "index";
    }

    @GetMapping("/meus")
    public String listarMeusProdutos(Model modelo, HttpSession sessao) {
        UsuarioDTO usuarioLogado = (UsuarioDTO) sessao.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/login";
        modelo.addAttribute("produtos", produtoService.listarPorUsuario(usuarioLogado.getId()));
        return "lista_produtos";
    }

    @GetMapping("/{id}")
    public String exibirDetalhe(@PathVariable("id") int id, Model modelo) {
        modelo.addAttribute("produto", produtoService.buscarPorId(id));
        return "detalhes_produto";
    }

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model modelo, HttpSession sessao) {
        UsuarioDTO usuarioLogado = (UsuarioDTO) sessao.getAttribute("usuarioLogado");
        ProdutoDTO dto = new ProdutoDTO();
        dto.setUsuario(usuarioLogado.getId());
        modelo.addAttribute("produtoDTO", dto);
        modelo.addAttribute("todasCategorias", categoriaService.listarAtivas());
        return "cadastrar_produto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@ModelAttribute("produtoDTO") ProdutoDTO produtoDTO,
                                RedirectAttributes atributos) {
        produtoService.salvar(produtoDTO);
        atributos.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") int id, Model modelo) {
        modelo.addAttribute("produtoDTO", produtoService.buscarPorId(id));
        modelo.addAttribute("todasCategorias", categoriaService.listarAtivas());
        return "cadastrar_produto";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarProduto(@PathVariable("id") int id,
                                           @ModelAttribute("produtoDTO") ProdutoDTO produtoDTO,
                                           RedirectAttributes atributos) {
        produtoService.atualizar(id, produtoDTO);
        atributos.addFlashAttribute("sucesso", "Produto atualizado com sucesso!");
        return "redirect:/produtos/meus";
    }

    @PostMapping("/avaliar/{id}")
    public String avaliarProduto(@PathVariable("id") int id,
                                 @RequestParam("nota") int nota,
                                 RedirectAttributes atributos) {
        try {
            produtoService.avaliar(id, nota);
            atributos.addFlashAttribute("sucesso", "Avaliação registrada!");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/produtos/" + id;
    }

    @PostMapping("/desativar/{id}")
    public String desativarProduto(@PathVariable("id") int id, RedirectAttributes atributos) {
        produtoService.desativar(id);
        atributos.addFlashAttribute("sucesso", "Produto desativado com sucesso!");
        return "redirect:/produtos";
    }
}
