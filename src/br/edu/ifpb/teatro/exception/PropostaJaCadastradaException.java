package br.edu.ifpb.teatro.exception;

public class PropostaJaCadastradaException extends Exception {

    public PropostaJaCadastradaException() {

        super("Já existe uma proposta identica cadastrada");

    }

}
