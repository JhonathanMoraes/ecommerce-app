package com.pp.ecommerce_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(nullable = false)
    private String status = "PENDENTE";

    @Column(nullable = false)
    private double total;

    @Column(name = "tipo_de_pagamento")
    private String tipoDePagamento;

    @Column
    private String endereco;

    @Column
    private double frete;

    @Column
    private int nota;

    public Pedido() {
    }

    public Pedido(Builder builder) {
        this.usuario = builder.usuario;
        this.produto = builder.produto;
        this.quantidade = builder.quantidade;
        this.tipoDePagamento = builder.tipoDePagamento;
        this.endereco = builder.endereco;
        this.frete = builder.frete;
    }
    
    public static Builder builder(Usuario usuario, Produto produto, int quantidade, String tipoDePagamento, String endereco, double frete){
        return new Builder(usuario, produto, quantidade, tipoDePagamento, endereco, frete);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipoDePagamento() {
        return tipoDePagamento;
    }

    public void setTipoDePagamento(String tipoDePagamento) {
        this.tipoDePagamento = tipoDePagamento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public void calcularTotal() {
        if (this.produto != null) {
            this.total = (this.produto.getPreco() * this.quantidade) + this.frete;
        }
    }
    
    public static class Builder{
        private int id;
        private Usuario usuario;
        private Produto produto;
        private int quantidade;
        private boolean ativo = true;
        private String status = "PENDENTE";
        private double total;
        private String tipoDePagamento;
        private String endereco;
        private double frete;
        private int nota;
        
        public Builder(Usuario usuario, Produto produto, int quantidade, String tipoDePagamento, String endereco, double frete){
            this.usuario = usuario;
            this.produto = produto;
            this.quantidade = quantidade;
            this.tipoDePagamento = tipoDePagamento;
            this.endereco = endereco;
            this.frete = frete;
        }
        
        public Builder id(int id){
            this.id = id;
            return this;
        }
        
        public Builder ativo(boolean ativo){
            this.ativo = ativo;
            return this;
        }
        
        public Builder status(String status){
            this.status = status;
            return this;
        }
        
        public Builder total(double total){
            this.total = total;
            return this;
        }
        
        public Builder nota(int nota){
            this.nota = nota;
            return this;
        }
        
        public Pedido build(){
            
            if(usuario == null || produto == null || tipoDePagamento == null){
                throw new IllegalStateException("Usuário, Produto e Tipo de pagamento são obrigatórios");
            }
                        
            return new Pedido(this);
        }
    }
}
