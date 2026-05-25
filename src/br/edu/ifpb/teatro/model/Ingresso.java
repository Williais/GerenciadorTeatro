package br.edu.ifpb.teatro.model;

import java.time.LocalDate;

public class Ingresso {
    private long id;
    private Pessoa comprador;
    private PropostaDeAluguel evento;
    private int quantidade;
    private LocalDate dataEmissao;

    public Ingresso(Pessoa comprador, PropostaDeAluguel evento, int quantidade) {
        this.comprador = comprador;
        this.evento = evento;
        this.quantidade = quantidade;
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

}
