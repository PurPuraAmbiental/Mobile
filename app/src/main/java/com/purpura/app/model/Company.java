package com.purpura.app.model;

public class Company {

    private final String cnpj;
    private String email;
    private String imagem;
    private String name;
    private String phone;

    public Company(String cnpj, String email, String imagem, String nome, String telefone) {
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
}
