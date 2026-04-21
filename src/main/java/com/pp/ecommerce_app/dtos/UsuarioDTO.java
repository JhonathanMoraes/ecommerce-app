package com.pp.ecommerce_app.dtos;

public class UsuarioDTO {

    private int id;
    private String email;
    private String senha;
    private String endereco;
    private boolean ativo;

    public UsuarioDTO() {
        super();
    }

    public UsuarioDTO(int id, String email, String senha, String endereco, boolean ativo) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.ativo = ativo;
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
}
