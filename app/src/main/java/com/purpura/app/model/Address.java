package com.purpura.app.model;

public class Address {

    private String nome;
    private String cep;
    private String complement;
    private int number;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Address(String nome, String cep, String complement, int number) {
        this.nome = nome;
        this.cep = cep;
        this.complement = complement;
        this.number = number;
    }
}
