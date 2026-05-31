package br.edu.ifpb.teatro.exception;

public class PessoaJaCadastradaException extends Exception {

    public PessoaJaCadastradaException() {

        super("Já existe uma pessoa cadastrada com esse CPF.");

    }

}

