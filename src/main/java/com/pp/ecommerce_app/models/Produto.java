package com.pp.ecommerce_app.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double preco;

    @Column(nullable = false)
    private int nota = 0;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "produto_categoria",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>();

    public Produto() {}

    public Produto(Builder builder) {
        this.nome = builder.nome;
        this.descricao = builder.descricao;
        this.quantidade = builder.quantidade;
        this.preco = builder.preco;
        this.nota = builder.nota;
        this.usuario = builder.usuario;
        this.ativo = builder.ativo;
    }
    
    public static Builder builder(String nome, String descricao, int quantidade, double preco, int nota, Usuario usuario, boolean ativo){
        return new Builder(nome, descricao, quantidade, preco, nota, usuario, ativo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota < 0 || nota > 10) {
            throw new IllegalArgumentException("A nota deve ser entre 0 e 10.");
        }
        this.nota = nota;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;

    }
    
    public static class Builder{
        private int id;
        private String nome;
        private String descricao;
        private int quantidade;
        private double preco;
        private int nota = 0;
        private Usuario usuario;
        private boolean ativo = true;
        
        public Builder(String nome, String descricao, int quantidade, double preco, int nota, Usuario usuario, boolean ativo){
            this.nome = nome;
            this.descricao = descricao;
            this.quantidade = quantidade;
            this.preco = preco;
            this.nota = nota;
            this.usuario = usuario;
            this.ativo = ativo;
        }
        
        public Builder id(int id){
            this.id = id;
            return this;
        }
        
        public Produto build(){
            if(usuario == null){
                throw new IllegalStateException("Usuário é obrigatório");
            }
            return new Produto(this);
        }
    }
}
