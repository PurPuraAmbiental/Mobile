package com.purpura.app.model;

public class EmpresaRequest {
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private String imagem;

    public EmpresaRequest(String nome, String cnpj, String email, String telefone, String imagem) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.telefone = telefone;
        this.imagem = imagem;
    }
    public EmpresaRequest(String cnpj, String telefone) {
        this.cnpj = cnpj;
        this.telefone = telefone;
    }
    public EmpresaRequest(String email, String nome, String imagem) {
        this.email = email;
        this.nome = nome;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCnpj() {
        return cnpj;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }



}
