package com.pp.ecommerce_app.controllers;

import com.pp.ecommerce_app.dtos.CategoriaDTO;
import com.pp.ecommerce_app.dtos.UsuarioDTO;
import com.pp.ecommerce_app.services.CategoriaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model modelo, HttpSession sessao) {
        UsuarioDTO usuarioLogado = (UsuarioDTO) sessao.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        modelo.addAttribute("categoriaDTO", new CategoriaDTO());
        return "cadastrar_categoria";
    }

    @PostMapping("/salvar")
    public String salvarCategoria(@ModelAttribute("categoriaDTO") CategoriaDTO categoriaDTO,
                                  RedirectAttributes atributos, HttpSession sessao) {
        UsuarioDTO usuarioLogado = (UsuarioDTO) sessao.getAttribute("usuarioLogado");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        try {
            categoriaService.salvar(categoriaDTO);
            atributos.addFlashAttribute("sucesso", "Categoria cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
            return "redirect:/categorias/novo";
        }
        return "redirect:/produtos/novo";
    }
}
