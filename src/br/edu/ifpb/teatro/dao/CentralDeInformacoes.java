package br.edu.ifpb.teatro.dao;

import br.edu.ifpb.teatro.model.ADM;
import br.edu.ifpb.teatro.model.Pessoa;
import br.edu.ifpb.teatro.model.PropostaDeAluguel;

import java.util.ArrayList;
import java.util.List;

public class CentralDeInformacoes {

    private List<Pessoa> todasAsPessoas = new ArrayList<>();
    private List<PropostaDeAluguel> todasAsPropostas = new ArrayList<>();
    private ADM administrador;

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

    public List<Pessoa> getTodasAsPessoas() {
        return todasAsPessoas;
    }

    public void setTodasAsPessoas(List<Pessoa> todasAsPessoas) {
        this.todasAsPessoas = todasAsPessoas;
    }

    public boolean adicionarPessoa(Pessoa p){
        if(recuperarPessoaPorCPF(p.getCpf()) != null){
            return false;
        }

        todasAsPessoas.add(p);
        return true;
    }

    public Pessoa recuperarPessoaPorCPF(String cpf){

        for (Pessoa p : todasAsPessoas){
            if(p.getCpf().equals(cpf)){
                return p;
            }
        }
        return null;
    }

    public boolean adicionarProposta(PropostaDeAluguel p){
        if(recuperarPropostaPorId(p.getId()) != null){
            return false;
        }

        todasAsPropostas.add(p);
        return true;
    }

    public PropostaDeAluguel recuperarPropostaPorId(long id){
        for (PropostaDeAluguel proposta : todasAsPropostas){
            if(proposta.getId() == id){
                return proposta;
            }
        }

        return null;
    }

    public List<PropostaDeAluguel> recuperarPropostasDeUmaPessoa(String cpf){
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
}
