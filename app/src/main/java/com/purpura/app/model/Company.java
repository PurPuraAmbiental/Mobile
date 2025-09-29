package com.purpura.app.model;

public class Company {

    private String hashUser;
    private String cnpj;
    private String email;
    private String imagem;
    private String name;
    private String phone;

    public Company(String hashUser, String cnpj, String email, String imagem, String nome, String telefone) {
        this.hashUser = hashUser;
        this.cnpj = cnpj;
        this.email = email;
        this.imagem = imagem;
        this.name = nome;
        this.phone = telefone;
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

    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHashUser() {
        return hashUser;
    }

    public void setHashUser(String hashUser) {
        this.hashUser = hashUser;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
