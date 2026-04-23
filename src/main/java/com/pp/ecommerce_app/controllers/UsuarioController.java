package com.pp.ecommerce_app.controllers;

import com.pp.ecommerce_app.dtos.UsuarioDTO;
import com.pp.ecommerce_app.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model modelo) {
        modelo.addAttribute("usuarioDTO", new UsuarioDTO());
        return "cadastrar_usuario";
    }

    @PostMapping("/salvar")
    public String salvarUsuario(@ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
                                RedirectAttributes atributos) {
        try {
            usuarioService.salvar(usuarioDTO);
            atributos.addFlashAttribute("sucesso", "Usuário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
            return "redirect:/usuarios/novo";
        }
        return "redirect:/login";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") int id, Model modelo) {
        modelo.addAttribute("usuarioDTO", usuarioService.buscarPorId(id));
        return "editar_usuario";
    }

    @PostMapping("/atualizar/{id}")
    public String atualizarUsuario(@PathVariable("id") int id,
                                   @ModelAttribute("usuarioDTO") UsuarioDTO usuarioDTO,
                                   RedirectAttributes atributos) {
        usuarioService.atualizar(id, usuarioDTO);
        atributos.addFlashAttribute("sucesso", "Usuário atualizado com sucesso!");
        return "redirect:/";
    }

    @PostMapping("/desativar/{id}")
    public String desativarUsuario(@PathVariable("id") int id, RedirectAttributes atributos) {
        usuarioService.desativar(id);
        atributos.addFlashAttribute("sucesso", "Usuário desativado com sucesso!");
        return "redirect:/login";
    }
}
