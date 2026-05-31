package br.edu.ifpb.teatro.exception;

public class HorarioForaDoTurnoPermitidoException extends Exception {

    public HorarioForaDoTurnoPermitidoException() {

        super("O horário informado ultrapassa os limites de um único turno permitido!");

    }
}

