package br.edu.ifpb.teatro.dao;

import br.edu.ifpb.teatro.exception.*;
import br.edu.ifpb.teatro.model.ADM;
import br.edu.ifpb.teatro.model.Ingresso;
import br.edu.ifpb.teatro.model.Pessoa;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;
import br.edu.ifpb.teatro.model.RegraDePreco;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CentralDeInformacoes {

    private List<Pessoa> todasAsPessoas = new ArrayList<>();
    private List<PropostaDeAluguel> todasAsPropostas = new ArrayList<>();
    private List<RegraDePreco> todasAsRegras = new ArrayList<>();
    private ADM administrador;
    private List<Ingresso> todosOsIngressos = new ArrayList<>();

    public ADM getAdministrador() {
        return administrador;
    }

    public void setAdministrador(ADM administrador) {
        this.administrador = administrador;
    }

    public List<PropostaDeAluguel> getTodasAsPropostas() {
        return todasAsPropostas;
    }

    public void setTodasAsPropostas(List<PropostaDeAluguel> todasAsPropostas) {
        this.todasAsPropostas = todasAsPropostas;
    }

    public List<Ingresso> getIngresso() {
        return todosOsIngressos;
    }

    public List<Pessoa> getTodasAsPessoas() {
        return todasAsPessoas;
    }

    public void setTodasAsPessoas(List<Pessoa> todasAsPessoas) {
        this.todasAsPessoas = todasAsPessoas;
    }

    public void validarHorarioLocacao(LocalDate data, LocalTime inicioPeca, LocalTime fimPeca) throws HorarioForaDoTurnoPermitidoException, HorarioIndisponivelException {

        LocalTime inicioReal = inicioPeca.minusHours(1);
        LocalTime fimReal = fimPeca.plusHours(1);

        boolean isManha = !inicioReal.isBefore(LocalTime.of(8, 0)) && !fimReal.isAfter(LocalTime.of(12, 0));
        boolean isTarde = !inicioReal.isBefore(LocalTime.of(13, 0)) && !fimReal.isAfter(LocalTime.of(18, 0));
        boolean isNoite = !inicioReal.isBefore(LocalTime.of(19, 0)) && !fimReal.isAfter(LocalTime.of(23, 0));

        if (!isManha && !isTarde && !isNoite) {
            throw new HorarioForaDoTurnoPermitidoException();
        }

        for (PropostaDeAluguel proposta : todasAsPropostas) {
            if (proposta.getDataEvento().equals(data)) {
                if (inicioReal.isBefore(proposta.getHoraFimLocacao()) && fimReal.isAfter(proposta.getHoraInicioLocacao())) {
                    throw new HorarioIndisponivelException();
                }
            }
        }
    }

    public void adicionarPessoa(Pessoa p) throws PessoaJaCadastradaException {

        if(recuperarPessoaPorCPF(p.getCpf()) != null){

            throw new PessoaJaCadastradaException();

        }

        todasAsPessoas.add(p);

    }

    public Pessoa recuperarPessoaPorCPF(String cpf){
        for (Pessoa p : todasAsPessoas){
            if(p.getCpf().equals(cpf)){
                return p;
            }
        }
        return null;
    }

    public void adicionarProposta(PropostaDeAluguel p) throws PropostaJaCadastradaException {

        if(recuperarPropostaPorId(p.getId()) != null){

            throw  new PropostaJaCadastradaException();

        }

        todasAsPropostas.add(p);

    }

    public void adicionarRegra(RegraDePreco novaRegra) {
        todasAsRegras.add(novaRegra);
    }

    public List<RegraDePreco> getTodasAsRegras() {
        if (todasAsRegras == null) {
            todasAsRegras = new ArrayList<>();
        }
        return todasAsRegras;
    }

    public PropostaDeAluguel recuperarPropostaPorId(long id){
        for (PropostaDeAluguel proposta : todasAsPropostas){
            if(proposta.getId() == id){
                return proposta;
            }
        }
        return null;
    }

    public List<PropostaDeAluguel> recuperarPropostasDeUmaPessoa (String cpf){

        if(recuperarPessoaPorCPF(cpf) == null){

            return null;

        }

        List<PropostaDeAluguel> encontrado = new ArrayList<>();
        for(PropostaDeAluguel p : todasAsPropostas){
            if (p.getLocatario().getCpf().equals(cpf)){
                encontrado.add(p);
            }
        }
        return encontrado;
    }

    public boolean realizarCompraDeIngresso(PropostaDeAluguel evento, Pessoa comprador, int qtd, float valor, String tipo) throws DadosCompraInvalidosException, PessoaJaCadastradaException{

        if (evento == null || comprador == null || qtd <= 0) {

            throw new DadosCompraInvalidosException();

        }

        if (recuperarPessoaPorCPF(comprador.getCpf()) == null){

            adicionarPessoa(comprador);

        }

        Ingresso ingresso = new Ingresso(comprador, evento, qtd, valor, tipo);

        todosOsIngressos.add(ingresso);

        return true;

    }

    public List<Ingresso> gerarListaDeIngressos(long id){

        List<Ingresso> ingressos = new ArrayList<>();

            for (Ingresso ingresso : todosOsIngressos){

                if (ingresso.getId() == id){

                    ingressos.add(ingresso);

                }

            }

        return ingressos;

    }
}