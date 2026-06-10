package com.pp.ecommerce_app.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private String endereco;

    @Column(nullable = false)
    private boolean ativo = true;

    public Usuario() {}

    public Usuario(Builder builder) {
        this.email = builder.email;
        this.senha = builder.senha;
        this.endereco = builder.endereco;
    }
    
    public static Builder builder(String email, String senha, String endereco){
        return new Builder(email, senha, endereco);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public static class Builder{
        private int id;
        private String email;
        private String senha;
        private String endereco;
        private boolean ativo = true;
        
        public Builder(String email, String senha, String endereco){
            this.email = email;
            this.senha = senha;
            this.endereco = endereco;
        }
        
        public Builder id(int id){
            this.id = id;
            return this;
        }
        
        public Builder ativo(boolean ativo){
            this.ativo = ativo;
            return this;
        }
        
        public Usuario build(){
            if(email == null || senha == null){
                throw new IllegalStateException("Email e senha são obrigatórios");
            }
            
            return new Usuario(this);
        }
    }
}
