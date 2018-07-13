package com.dynatron.projeto.massagem.Objetos;

/**
 * Created by User on 11/07/2018.
 */

public class Registros {
    private String id;
    private String tipo;
    private String descricao;
    private String data;
    private String valor;

    public Registros() {
    }

    public Registros(String descricao, String data, String valor) {
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Registros{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data='" + data + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
