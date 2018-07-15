package com.dynatron.projeto.massagem.Objetos;

/**
 * Created by User on 13/07/2018.
 */

public class Cliente {

    private String nome;
    private String endereço;
    private String numTotal;
    private String numMes;

    public Cliente() {
    }

    public Cliente(String nome, String endereço) {
        this.nome = nome;
        this.endereço = endereço;
    }

    public Cliente(String nome, String endereço, String numTotal, String numMes) {
        this.nome = nome;
        this.endereço = endereço;
        this.numTotal = numTotal;
        this.numMes = numMes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public String getNumTotal() {
        return numTotal;
    }

    public void setNumTotal(String numTotal) {
        this.numTotal = numTotal;
    }

    public String getNumMes() {
        return numMes;
    }

    public void setNumMes(String numMes) {
        this.numMes = numMes;
    }
}
