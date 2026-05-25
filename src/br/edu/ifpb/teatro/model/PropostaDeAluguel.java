package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.StatusProposta;
import java.io.Serializable;
import java.time.LocalDateTime; // alterando para datatime para pegar a hora tambem ja que é um dos requisitos nas regras de preco

public class PropostaDeAluguel implements Serializable { // O implements Serializable permite que o Java transforme este objeto em dados (bytes/XML)
                                                        // para que ele possa ser salvo em arquivos e recuperado depois sem perder as informações.

    // nao sei como acessar quando o main me pede o id, ent vou colocar com incrementação e depois eu coloco o currentTimeMillis
    private long id = 0; //currentTimeMillis();
    private LocalDateTime dataDeCadastro;
    private LocalDateTime dataDeInicioDoAluguel;
    private LocalDateTime dataDeFimDoAluguel;
    private String nomeDaPeca;
    private float valorTotalDoAluguel;
    private Pessoa locatario;
    private StatusProposta status;

    public PropostaDeAluguel(LocalDateTime dataDeCadastro, LocalDateTime dataDeInicioDoAluguel, LocalDateTime dataDeFimDoAluguel, String nomeDaPeca, float valorTotalDoAluguel, Pessoa locatario) {
        this.dataDeCadastro = dataDeCadastro;
        this.dataDeInicioDoAluguel = dataDeInicioDoAluguel;
        this.dataDeFimDoAluguel = dataDeFimDoAluguel;
        this.nomeDaPeca = nomeDaPeca;
        this.valorTotalDoAluguel = valorTotalDoAluguel;
        this.locatario = locatario;
        this.status = StatusProposta.EM_CONTRATACAO;
        this.id ++;
    }

    public LocalDateTime getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(LocalDateTime dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public LocalDateTime getDataDeInicioDoAluguel() {
        return dataDeInicioDoAluguel;
    }

    public void setDataDeInicioDoAluguel(LocalDateTime dataDeInicioDoAluguel) {
        this.dataDeInicioDoAluguel = dataDeInicioDoAluguel;
    }

    public LocalDateTime getDataDeFimDoAluguel() {
        return dataDeFimDoAluguel;
    }

    public void setDataDeFimDoAluguel(LocalDateTime dataDeFimDoAluguel) {
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
        if (status == StatusProposta.CONTRATADO && LocalDateTime.now().isAfter(getDataDeFimDoAluguel())) {
            status = StatusProposta.ENCERRADO;
            return false;
        }

        if (status == StatusProposta.EM_CONTRATACAO && LocalDateTime.now().isAfter(getDataDeCadastro().plusDays(2))) {
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
