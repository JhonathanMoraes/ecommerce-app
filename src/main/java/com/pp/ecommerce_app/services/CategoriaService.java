package com.pp.ecommerce_app.services;

import com.pp.ecommerce_app.dtos.CategoriaDTO;
import com.pp.ecommerce_app.models.Categoria;
import com.pp.ecommerce_app.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private CategoriaDTO paraDTO(Categoria categoria) {
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.isAtivo()
        );
    }

    private Categoria paraEntidade(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setAtivo(dto.isAtivo());
        return categoria;
    }

    public List<CategoriaDTO> listarAtivas() {
        return categoriaRepository.findAllByAtivo(true)
                .stream()
                .map(this::paraDTO)
                .collect(Collectors.toList());
    }

    public List<Categoria> buscarEntidadesPorIds(List<Integer> ids) {
        return categoriaRepository.findAllById(ids);
    }

    public Categoria buscarEntidadePorId(int id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: id=" + id));
    }

    public CategoriaDTO salvar(CategoriaDTO dto) {
        if (categoriaRepository.existsByNome(dto.getNome())) {
            throw new IllegalArgumentException("Categoria já cadastrada: " + dto.getNome());
        }
        Categoria categoria = paraEntidade(dto);
        categoria.setAtivo(true);
        return paraDTO(categoriaRepository.save(categoria));
    }
}
