package com.purpura.app.model;

public class EmpresaRequest {

    private final String cnpj; // único e imutável
    private String email;
    private String imagem;
    private String nome;
    private String telefone;

    // Construtor único (todos os campos obrigatórios)
    public EmpresaRequest(String cnpj, String email, String imagem, String nome, String telefone) {
        this.cnpj = cnpj;
        this.email = email;
        this.imagem = imagem;
        this.nome = nome;
        this.telefone = telefone;
    }

    // ============================
    // GETTERS E SETTERS
    // ============================

    public String getCnpj() {
        return cnpj;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
