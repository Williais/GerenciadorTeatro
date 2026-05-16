package br.edu.ifpb.teatro.security;

public class ValidadorSenha {
    public static void validandoSenha(String senha){

        if (senha.length() < 8) {
            throw new RuntimeException("A senha precisa ter, no minimo, 8 digitos.");
        }

        if (senha.matches("(\\d)\\1{8}")) {
            throw new RuntimeException("a senha não pode ser composta por dígitos repetidos.");
        }
    }
}
