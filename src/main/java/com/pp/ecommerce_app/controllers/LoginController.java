package com.pp.ecommerce_app.controllers;

import com.pp.ecommerce_app.dtos.UsuarioDTO;
import com.pp.ecommerce_app.services.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String exibirLogin() {
        return "login/login";
    }

    @PostMapping("/login")
    public String processarLogin(@RequestParam("email") String email,
                                 @RequestParam("senha") String senha,
                                 HttpSession sessao,
                                 RedirectAttributes atributos) {

        Optional<UsuarioDTO> resultado = usuarioService.autenticar(email, senha);

        if (resultado.isEmpty()) {
            atributos.addFlashAttribute("erro", "Email ou senha inválidos.");
            return "redirect:/login";
        }

        sessao.setAttribute("usuarioLogado", resultado.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String realizarLogout(HttpSession sessao) {
        sessao.invalidate();
        return "redirect:/login";
    }
}
