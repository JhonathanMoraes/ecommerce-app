package com.pp.ecommerce_app.services;

import com.pp.ecommerce_app.dtos.ProdutoDTO;
import com.pp.ecommerce_app.models.Produto;
import com.pp.ecommerce_app.models.Usuario;
import com.pp.ecommerce_app.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioService usuarioService;

    private ProdutoDTO paraDTO(Produto produto) {
        return new ProdutoDTO(
            produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getQuantidade(),
            produto.getPreco(),
            produto.getNota(),
            produto.getUsuario() != null ? produto.getUsuario().getId() : 0,
            produto.isAtivo()
        );
    }

    private Produto paraEntidade(ProdutoDTO dto) {
        Usuario usuario = usuarioService.buscarEntidadePorId(dto.getUsuario());

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
        produto.setNota(dto.getNota());
        produto.setUsuario(usuario);
        produto.setAtivo(dto.isAtivo());
        return produto;
    }

    public List<ProdutoDTO> listarAtivos() {
        return produtoRepository.findAllByAtivo(true)
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarAtivosPorUsuario(int usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return produtoRepository.findAllByUsuarioAndAtivo(usuario, true)
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(int id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        return paraDTO(produto);
    }

    public Produto buscarEntidadePorId(int id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
    }

    public ProdutoDTO salvar(ProdutoDTO dto) {
        Produto salvo = produtoRepository.save(paraEntidade(dto));
        return paraDTO(salvo);
    }

    public ProdutoDTO atualizar(int id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());

        return paraDTO(produtoRepository.save(produto));
    }

    public void avaliar(int id, int nota) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        produto.setNota(nota); // setNota já valida o intervalo 0-10
        produtoRepository.save(produto);
    }

    public void desativar(int id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
