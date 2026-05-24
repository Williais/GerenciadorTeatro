package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.StatusProposta;

import java.time.LocalDate;
import java.time.LocalTime;

public class PropostaDeAluguel {

    private long id = System.currentTimeMillis();
    private LocalDate dataDoEvento;
    private LocalTime horaInicioLocacao;
    private LocalTime horaFimLocacao;
    private float precoDoIngresso;
    private LocalDate dataDeCadastro;
    private String nomeDaPeca;
    private float valorTotalDoAluguel;
    private Pessoa locatario;
    private StatusProposta status;

    public PropostaDeAluguel(LocalDate dataDeCadastro, String nomeDaPeca, float valorTotalDoAluguel, Pessoa locatario, LocalDate dataDoEvento, LocalTime horaInicioLocacao, LocalTime horaFimLocacao, float precoDoIngresso) {
        this.dataDeCadastro = dataDeCadastro;
        this.nomeDaPeca = nomeDaPeca;
        this.valorTotalDoAluguel = valorTotalDoAluguel;
        this.locatario = locatario;
        this.status = StatusProposta.EM_CONTRATACAO;
        this.dataDoEvento = dataDoEvento;
        this.horaFimLocacao = horaFimLocacao;
        this.horaInicioLocacao = horaInicioLocacao;
        this.precoDoIngresso = precoDoIngresso;
    }

    public float getPrecoDoIngresso() {
        return precoDoIngresso;
    }

    public void setPrecoDoIngresso(float precoDoIngresso) {
        this.precoDoIngresso = precoDoIngresso;
    }

    public LocalTime getHoraFimLocacao() {
        return horaFimLocacao;
    }

    public void setHoraFimLocacao(LocalTime horaFimLocacao) {
        this.horaFimLocacao = horaFimLocacao;
    }

    public LocalTime getHoraInicioLocacao() {
        return horaInicioLocacao;
    }

    public void setHoraInicioLocacao(LocalTime horaInicioLocacao) {
        this.horaInicioLocacao = horaInicioLocacao;
    }

    public LocalDate getDataDoEvento() {
        return dataDoEvento;
    }

    public void setDataDoEvento(LocalDate dataDoEvento) {
        this.dataDoEvento = dataDoEvento;
    }

    public LocalDate getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(LocalDate dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public String getNomeDaPeca() {
        return nomeDaPeca;
    }

    public void setNomeDaPeca(String nomeDaPeca) {
        this.nomeDaPeca = nomeDaPeca;
    }

    public float getValorTotalDoAluguel() {
        return valorTotalDoAluguel;
    }

    public void setValorTotalDoAluguel(float valorTotalDoAluguel) {
        this.valorTotalDoAluguel = valorTotalDoAluguel;
    }

    public Pessoa getLocatario() {
        return locatario;
    }

    public void setLocatario(Pessoa locatario) {
        this.locatario = locatario;
    }

    public StatusProposta getStatus() {
        return status;
    }

    public void setStatus(StatusProposta status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public boolean isAtivo() {
        if (status == StatusProposta.CONTRATADO && LocalDate.now().isAfter(getDataDoEvento())) {
            status = StatusProposta.ENCERRADO;
            return false;
        }

        if (status == StatusProposta.EM_CONTRATACAO && LocalDate.now().isAfter(getDataDeCadastro().plusDays(2))) {
            status = StatusProposta.ENCERRADO;
            return false;
        }

        return status == StatusProposta.CONTRATADO;

    }

    public String toString(){
        String ativo = "";

        if(isAtivo()){
            ativo = "Sim";
        }else {
            ativo = "Não";
        }
        return "Nome da Peça: " + nomeDaPeca + "\n"
                + "Nome do Locatário: " + locatario.getNome() + " (CPF: " + locatario.getCpf() + ") " + "\n"
                + "Está Ativo?: " + ativo + "\n"
                + "Status: " + status;
    }
}
