package br.edu.ifpb.teatro.exception;

import javax.swing.*;

public class HorarioForaDoTurnoPermitidoException extends Exception {

    public HorarioForaDoTurnoPermitidoException() {

        super("O horário informado ultrapassa os limites de um único turno permitido!");
        JOptionPane.showMessageDialog(null,"erro na questão do Turno... Lembre-se que o sistema adiciona automaticamente 1 hora antes e 1 hora depois para chegada e saída do público. O período total do aluguel não pode ultrapassar as bordas de um único turno (8-12, 13-18 ou 19-23).", "Erro", JOptionPane.ERROR_MESSAGE);

    }
}

