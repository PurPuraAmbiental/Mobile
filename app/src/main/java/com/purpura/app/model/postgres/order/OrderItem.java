package com.purpura.app.model.postgres.order;

public class OrderItem {

    private Long id;
    private String idResiduo;
    private Double preco;
    private Integer quantidade;
    private Double peso;
    private String tipoUnidade;

    public OrderItem(Long id, String idResiduo, Double preco, Integer quantidade, Double peso, String tipoUnidade) {
        this.id = id;
        this.idResiduo = idResiduo;
        this.preco = preco;
        this.quantidade = quantidade;
        this.peso = peso;
        this.tipoUnidade = tipoUnidade;
    }

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdResiduo() {
        return idResiduo;
    }

    public void setIdResiduo(String idResiduo) {
        this.idResiduo = idResiduo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getTipoUnidade() {
        return tipoUnidade;
    }

    public void setTipoUnidade(String tipoUnidade) {
        this.tipoUnidade = tipoUnidade;
    }
}
