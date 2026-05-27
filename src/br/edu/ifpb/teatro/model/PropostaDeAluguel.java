package br.edu.ifpb.teatro.model;

import br.edu.ifpb.teatro.enums.StatusProposta;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PropostaDeAluguel implements Serializable {

    private long id;
    private LocalDate dataEvento;
    private LocalDateTime dataDeCadastro;
    private LocalTime horaInicioLocacao;
    private LocalTime horaFimLocacao;
    private String nomeDaPeca;
    private float valorTotalDoAluguel;
    private float precoDoIngresso;
    private Pessoa locatario;
    private StatusProposta status;

    public PropostaDeAluguel(LocalDateTime dataDeCadastro, LocalTime horaInicioLocacao, LocalTime horaFimLocacao,
                             String nomeDaPeca, float valorTotalDoAluguel, float precoDoIngresso, Pessoa locatario, LocalDate dataEvento) {
        this.id = System.currentTimeMillis();
        this.dataDeCadastro = dataDeCadastro;
        this.horaInicioLocacao = horaInicioLocacao;
        this.horaFimLocacao = horaFimLocacao;
        this.nomeDaPeca = nomeDaPeca;
        this.valorTotalDoAluguel = valorTotalDoAluguel;
        this.precoDoIngresso = precoDoIngresso;
        this.locatario = locatario;
        this.status = StatusProposta.EM_CONTRATACAO;
        this.dataEvento = dataEvento;
    }

    public long getId() { return id; }

    public LocalDate getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDate dataEvento) { this.dataEvento = dataEvento; }

    public LocalDateTime getDataDeCadastro() { return dataDeCadastro; }
    public void setDataDeCadastro(LocalDateTime dataDeCadastro) { this.dataDeCadastro = dataDeCadastro; }

    public LocalTime getHoraInicioLocacao() { return horaInicioLocacao; }
    public void setHoraInicioLocacao(LocalTime horaInicioLocacao) { this.horaInicioLocacao = horaInicioLocacao; }

    public LocalTime getHoraFimLocacao() { return horaFimLocacao; }
    public void setHoraFimLocacao(LocalTime horaFimLocacao) { this.horaFimLocacao = horaFimLocacao; }

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

    public boolean isAtivo() {
        LocalDateTime agora = LocalDateTime.now();

        LocalDateTime fimDoEventoCompleto = LocalDateTime.of(dataEvento, horaFimLocacao);

        if (status == StatusProposta.CONTRATADO && agora.isAfter(fimDoEventoCompleto)) {
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
                + "Data: " + dataEvento + " (" + horaInicioLocacao + " - " + horaFimLocacao + ")\n"
                + "Ativo: " + ativo + "\n"
                + "Status: " + status;
    }
}