package com.pp.ecommerce_app.services;

import com.pp.ecommerce_app.dtos.ProdutoDTO;
import com.pp.ecommerce_app.models.Produto;
import com.pp.ecommerce_app.models.Usuario;
import com.pp.ecommerce_app.models.Categoria;
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

    @Autowired
    private CategoriaService categoriaService;

    private ProdutoDTO dto(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO(
            produto.getId(),
            produto.getNome(),
            produto.getDescricao(),
            produto.getQuantidade(),
            produto.getPreco(),
            produto.getNota(),
            produto.getUsuario() != null ? produto.getUsuario().getId() : 0,
            produto.getUsuario() != null ? produto.getUsuario().getEmail() : "",
            produto.isAtivo()
        );
        dto.setCategorias(produto.getCategorias().stream().map(Categoria::getId).collect(Collectors.toList()));
        return dto;
    }

    private Produto entidade(ProdutoDTO dto) {
        Usuario usuario = usuarioService.buscarEntidadePorId(dto.getUsuario());


        Produto produto = Produto.builder(
            dto.getNome(),
            dto.getDescricao(),
            dto.getQuantidade(),
            dto.getPreco(),
            dto.getNota(),
            usuario,
            dto.isAtivo()
            )
            .build();
        if (dto.getCategorias() != null && !dto.getCategorias().isEmpty()) {
            produto.setCategorias(categoriaService.buscarEntidadesPorIds(dto.getCategorias()));
        }

        return produto;
    }


    public List<ProdutoDTO> listarAtivos() {
        return produtoRepository.findAllByAtivo(true)
                .stream()
                .map(this::dto)
                .collect(Collectors.toList());
    }

    public List<ProdutoDTO> listarPorUsuario(int usuarioId) {
        Usuario usuario = usuarioService.buscarEntidadePorId(usuarioId);
        return produtoRepository.findAllByUsuarioAndAtivo(usuario, true)
                .stream()
                .map(this::dto)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarPorId(int id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        return dto(produto);
    }

    public Produto buscarEntidadePorId(int id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
    }

    public ProdutoDTO salvar(ProdutoDTO dto) {
        Produto entidade = entidade(dto);
        entidade.setAtivo(true);
        Produto produto = produtoRepository.save(entidade);
        return dto(produto);
    }

    public ProdutoDTO atualizar(int id, ProdutoDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());
        produto.setAtivo(true);
        if (dto.getCategorias() != null) {
            produto.setCategorias(categoriaService.buscarEntidadesPorIds(dto.getCategorias()));
        } else {
            produto.setCategorias(new java.util.ArrayList<>());
        }
        return dto(produtoRepository.save(produto));
    }


    public void avaliar(int id, int nota) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        produto.setNota(nota);
        produtoRepository.save(produto);
    }

    public void atualizarQuantidadeEmEstoque(int id, int quantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        if (produto.getQuantidade() < quantidade) {
            throw new IllegalArgumentException("Quantidade indisponível em estoque.");
        }
        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoRepository.save(produto);
    }

    public void desativar(int id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: id=" + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
