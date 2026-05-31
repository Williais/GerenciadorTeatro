package br.edu.ifpb.teatro.exception;

public class ValorIngressoInvalidoException extends Exception {

    public ValorIngressoInvalidoException() {

        super("Valor do ingresso inserido está invalido");

    }


    public ValorIngressoInvalidoException(String message) {

        super(message);

    }

}
