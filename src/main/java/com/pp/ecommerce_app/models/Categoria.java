package com.pp.ecommerce_app.models;

import jakarta.persistence.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alunocmc
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private boolean ativo = true;

    public Categoria() {
    }

    private Categoria(Builder builder) {
        this.id = builder.id;
        this.nome = builder.nome;
        this.ativo = builder.ativo;
    }

    public static Builder builder(String nome) {
        return new Builder(nome);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public static class Builder {
        private int id;
        private String nome;
        private boolean ativo = true;

        private Builder(String nome) {
            this.nome = nome;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder ativo(boolean ativo) {
            this.ativo = ativo;
            return this;
        }

        public Categoria build() {
            if(nome == null){
                throw new IllegalStateException("Nome é obrigatório.");
            }

            return new Categoria(this);
        }
    }
}
