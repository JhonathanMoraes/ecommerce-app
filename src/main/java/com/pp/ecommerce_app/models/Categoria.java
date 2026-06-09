package com.pp.ecommerce_app.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alunocmc
 */
public class Categoria {
    private int id;
    private String nome;
    private boolean ativo;
    
    private Categoria(Builder builder){
        this.nome = builder.nome;
    }
    
    public static Builder builder(String nome){
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
    
    
    
    public static class Builder{
        private int id;
        private String nome;
        private boolean ativo = true;
        
        private Builder(String nome){
            this.nome = nome;
        }
        
        public Builder id(int id){
            this.id = id;
            return this;
        }
        
        public Builder ativo(boolean ativo){
            this.ativo = ativo;
            return this;
        }
        
        public Categoria build(){
            if(nome == null){
                throw new IllegalStateException("Nome é obrigatório.");
            }
            
            return new Categoria(this);
        }
    }
    
}
