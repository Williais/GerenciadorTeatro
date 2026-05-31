package br.edu.ifpb.teatro.exception;

public class HorarioIndisponivelException extends Exception {

    public HorarioIndisponivelException() {

        super("Já existe um evento ocupando esse horário.");

    }

}
