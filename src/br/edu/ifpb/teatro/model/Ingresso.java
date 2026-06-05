package br.edu.ifpb.teatro.model;

import java.time.LocalDate;

public class Ingresso {
    private long id;
    private Pessoa comprador;
    private PropostaDeAluguel evento;
    private int quantidade;
    private LocalDate dataEmissao;
    private float valorIngresso;
    private String tipo;

    public Ingresso(Pessoa comprador, PropostaDeAluguel evento, int quantidade, float valorIngresso, String tipo) {
        this.comprador = comprador;
        this.tipo = tipo;
        this.valorIngresso = valorIngresso;
        this.evento = evento;
        this.quantidade = quantidade;
        this.dataEmissao = LocalDate.now();
        this.id = System.currentTimeMillis();
    }



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public Pessoa getComprador() {
        return comprador;
    }

    public void setComprador(Pessoa comprador) {
        this.comprador = comprador;
    }

    public PropostaDeAluguel getEvento() {
        return evento;
    }

    public void setEvento(PropostaDeAluguel evento) {
        this.evento = evento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public float getValorIngresso() {
        return valorIngresso;
    }

    public void setValorIngresso(float valorIngresso) {
        this.valorIngresso = valorIngresso;
    }
}
