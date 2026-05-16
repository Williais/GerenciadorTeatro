package br.edu.ifpb.teatro.model;

import java.time.LocalDate;

public class RegraDePreco {
    private long id;
    private float valorHora;
    private LocalDate periodoAplicacao;

    public RegraDePreco(long id, float valorHora, LocalDate periodoAplicacao) {
        this.id = id;
        this.valorHora = valorHora;
        this.periodoAplicacao = periodoAplicacao;
    }

    public long getId() {
        return id;
    }

    public float getValorHora() {
        return valorHora;
    }

    public void setValorHora(float valorHora) {
        this.valorHora = valorHora;
    }

    public LocalDate getPeriodoAplicacao() {
        return periodoAplicacao;
    }

    public void setPeriodoAplicacao(LocalDate periodoAplicacao) {
        this.periodoAplicacao = periodoAplicacao;
    }
}
