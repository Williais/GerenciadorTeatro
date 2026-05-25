package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.StatusProposta;
import java.io.Serializable;
import java.time.LocalDateTime;

public class PropostaDeAluguel implements Serializable {

    private long id;
    private LocalDateTime dataDeCadastro;
    private LocalDateTime inicioEvento; // Conciliado: substitui dataDeInicioDoAluguel e dataDoEvento
    private LocalDateTime fimEvento;    // Conciliado: substitui dataDeFimDoAluguel e horaFimLocacao
    private String nomeDaPeca;
    private float valorTotalDoAluguel;
    private float precoDoIngresso;      // Adicionado da versão de will
    private Pessoa locatario;
    private StatusProposta status;

    public PropostaDeAluguel(LocalDateTime dataDeCadastro, LocalDateTime inicioEvento, LocalDateTime fimEvento,
                             String nomeDaPeca, float valorTotalDoAluguel, float precoDoIngresso, Pessoa locatario) {
        this.id = System.currentTimeMillis();
        this.dataDeCadastro = dataDeCadastro;
        this.inicioEvento = inicioEvento;
        this.fimEvento = fimEvento;
        this.nomeDaPeca = nomeDaPeca;
        this.valorTotalDoAluguel = valorTotalDoAluguel;
        this.precoDoIngresso = precoDoIngresso;
        this.locatario = locatario;
        this.status = StatusProposta.EM_CONTRATACAO;
    }

    public long getId() { return id; }

    public LocalDateTime getDataDeCadastro() { return dataDeCadastro; }
    public void setDataDeCadastro(LocalDateTime dataDeCadastro) { this.dataDeCadastro = dataDeCadastro; }

    public LocalDateTime getInicioEvento() { return inicioEvento; }
    public void setInicioEvento(LocalDateTime inicioEvento) { this.inicioEvento = inicioEvento; }

    public LocalDateTime getFimEvento() { return fimEvento; }
    public void setFimEvento(LocalDateTime fimEvento) { this.fimEvento = fimEvento; }

    public String getNomeDaPeca() { return nomeDaPeca; }
    public void setNomeDaPeca(String nomeDaPeca) { this.nomeDaPeca = nomeDaPeca; }

    public float getValorTotalDoAluguel() { return valorTotalDoAluguel; }
    public void setValorTotalDoAluguel(float valorTotalDoAluguel) { this.valorTotalDoAluguel = valorTotalDoAluguel; }

    public float getPrecoDoIngresso() { return precoDoIngresso; }
    public void setPrecoDoIngresso(float precoDoIngresso) { this.precoDoIngresso = precoDoIngresso; }

    public Pessoa getLocatario() { return locatario; }
    public void setLocatario(Pessoa locatario) { this.locatario = locatario; }

    public StatusProposta getStatus() { return status; }
    public void setStatus(StatusProposta status) { this.status = status; }

    // Lógica de Atividade (Usando a nova variável inicioEvento)
    public boolean isAtivo() {
        LocalDateTime agora = LocalDateTime.now();

        if (status == StatusProposta.CONTRATADO && agora.isAfter(fimEvento)) {
            status = StatusProposta.ENCERRADO;
            return false;
        }

        if (status == StatusProposta.EM_CONTRATACAO && agora.isAfter(dataDeCadastro.plusDays(2))) {
            status = StatusProposta.ENCERRADO;
            return false;
        }

        return status == StatusProposta.CONTRATADO;
    }

    @Override
    public String toString() {
        String ativo = isAtivo() ? "Sim" : "Não";
        return "Peça: " + nomeDaPeca + "\n"
                + "Locatário: " + locatario.getNome() + "\n"
                + "Início: " + inicioEvento + "\n"
                + "Ativo: " + ativo + "\n"
                + "Status: " + status;
    }
}