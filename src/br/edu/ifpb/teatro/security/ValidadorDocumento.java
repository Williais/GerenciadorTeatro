package br.edu.ifpb.teatro.security;

public class ValidadorDocumento {

    public static void validarCPF(String cpf){
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            throw new RuntimeException("O CPF precisa ter 11 dígitos.");
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            throw new RuntimeException("CPF Inválido: Não são permitidos dígitos repetidos.");
        }

        int soma = 0;
        int peso = 10;

        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * peso;
            peso --;
        }

        int resto = 11 - (soma % 11);
        int primeiroDigito = (resto > 9) ? 0 : resto;

        soma = 0;
        peso = 11;

        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * peso;
            peso--;
        }

        resto = 11 - (soma % 11);
        int segundoDigito = (resto > 9) ? 0 : resto;

        int digito1Validacao = cpf.charAt(9) - '0';
        int digito2Validacao = cpf.charAt(10) - '0';

        if(primeiroDigito != digito1Validacao || segundoDigito != digito2Validacao){
            throw new RuntimeException("CPF Inválido");
        }


    }
}
