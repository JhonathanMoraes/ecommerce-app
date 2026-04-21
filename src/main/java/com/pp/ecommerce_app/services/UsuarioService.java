package com.pp.ecommerce_app.services;

import com.pp.ecommerce_app.dtos.UsuarioDTO;
import com.pp.ecommerce_app.models.Usuario;
import com.pp.ecommerce_app.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioDTO paraDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getEmail(),
            null,
            usuario.getEndereco(),
            usuario.isAtivo()
        );
    }

    private Usuario paraEntidade(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setEndereco(dto.getEndereco());
        usuario.setAtivo(dto.isAtivo());
        return usuario;
    }

    public Optional<UsuarioDTO> autenticar(String email, String senha) {
        return usuarioRepository
                .findByEmailAndSenha(email, senha)
                .map(this::paraDTO);
    }

    public UsuarioDTO salvar(UsuarioDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + dto.getEmail());
        }
        Usuario salvo = usuarioRepository.save(paraEntidade(dto));
        return paraDTO(salvo);
    }

    public UsuarioDTO buscarPorId(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: id=" + id));
        return paraDTO(usuario);
    }

    public Usuario buscarEntidadePorId(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: id=" + id));
    }

    public List<UsuarioDTO> listarAtivos() {
        return usuarioRepository.findAllByAtivo(true)
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO atualizar(int id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: id=" + id));

        usuario.setEmail(dto.getEmail());
        usuario.setEndereco(dto.getEndereco());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(dto.getSenha());
        }

        return paraDTO(usuarioRepository.save(usuario));
    }

    public void desativar(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: id=" + id));
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
