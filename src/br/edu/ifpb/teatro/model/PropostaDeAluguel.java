package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.PropostaDeAluguelStatus;

import java.time.LocalDate;

import static java.lang.System.currentTimeMillis;

public class PropostaDeAluguel {

    // nao sei como acessar quando o main me pede o id, ent vou colocar com incrementação e depois eu coloco o currentTimeMillis
    private long id = 0; //currentTimeMillis();
    private LocalDate dataDeCadastro;
    private LocalDate dataDeInicioDoAluguel;
    private LocalDate dataDeFimDoAluguel;
    private String nomeDaPeca;
    private float valorTotalDoAluguel;
    private Pessoa locatario;
    private PropostaDeAluguelStatus status;

    public PropostaDeAluguel(LocalDate dataDeCadastro, LocalDate dataDeInicioDoAluguel, LocalDate dataDeFimDoAluguel, String nomeDaPeca, float valorTotalDoAluguel, Pessoa locatario) {
        this.dataDeCadastro = dataDeCadastro;
        this.dataDeInicioDoAluguel = dataDeInicioDoAluguel;
        this.dataDeFimDoAluguel = dataDeFimDoAluguel;
        this.nomeDaPeca = nomeDaPeca;
        this.valorTotalDoAluguel = valorTotalDoAluguel;
        this.locatario = locatario;
        this.status = PropostaDeAluguelStatus.EM_AVALIACAO;
        this.id ++;
    }

    public LocalDate getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(LocalDate dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public LocalDate getDataDeInicioDoAluguel() {
        return dataDeInicioDoAluguel;
    }

    public void setDataDeInicioDoAluguel(LocalDate dataDeInicioDoAluguel) {
        this.dataDeInicioDoAluguel = dataDeInicioDoAluguel;
    }

    public LocalDate getDataDeFimDoAluguel() {
        return dataDeFimDoAluguel;
    }

    public void setDataDeFimDoAluguel(LocalDate dataDeFimDoAluguel) {
        this.dataDeFimDoAluguel = dataDeFimDoAluguel;
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

    public PropostaDeAluguelStatus getStatus() {
        return status;
    }

    public void setStatus(PropostaDeAluguelStatus status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public boolean isAtivo() {
        if (status == PropostaDeAluguelStatus.ATIVO && LocalDate.now().isAfter(getDataDeFimDoAluguel())) {
            status = PropostaDeAluguelStatus.INATIVO;
            return false;
        }

        if (status == PropostaDeAluguelStatus.EM_AVALIACAO && LocalDate.now().isAfter(getDataDeCadastro().plusDays(2))) {
            status = PropostaDeAluguelStatus.NAO_CONTRATADO;
            return false;
        }

        return status == PropostaDeAluguelStatus.ATIVO;

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
