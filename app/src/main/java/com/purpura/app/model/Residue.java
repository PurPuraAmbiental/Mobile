package com.purpura.app.model;

public class Residue {
    private String id;
    private String nome;
    private String descricao;
    private double preco;
    private double peso;
    private String tipoUnidade;
    private int estoque;

    private String urlFoto;
    private String idChavePix;
    private String idEndereco;

    public Residue(String id, String nome, String descricao, double peso, double preco, int estoque, String tipoUnidade, String urlFoto, String idChavePix, String idEndereco) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.peso = peso;
        this.tipoUnidade = tipoUnidade;
        this.estoque = estoque;
        this.urlFoto = urlFoto;
        this.idChavePix = idChavePix;
        this.idEndereco = idEndereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }
}
