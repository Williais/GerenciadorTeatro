package br.edu.ifpb.teatro.exception;

import javax.swing.*;

public class CPFInvalidoException extends Exception {

    public CPFInvalidoException(String message) {
        super(message);
        JOptionPane.showMessageDialog(
                null,
                "CPF Invalido. Coloque um CPF Válido",
                "Erro", JOptionPane.ERROR_MESSAGE);
    }

}
