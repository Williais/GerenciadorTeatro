package br.edu.ifpb.teatro.security;

import br.edu.ifpb.teatro.exception.SenhaInvalidaException;

public class ValidadorSenha {
    public static void validandoSenha(String senha) throws SenhaInvalidaException {

        if (senha.length() < 8) {
            throw new SenhaInvalidaException("A senha precisa ter, no minimo, 8 digitos.");
        }

        if (senha.matches("(\\d)\\1{6}")) {
            throw new SenhaInvalidaException("a senha não pode ser composta por dígitos repetidos.");
        }
    }
}
