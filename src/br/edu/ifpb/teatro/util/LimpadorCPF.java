package br.edu.ifpb.teatro.util;

public class LimpadorCPF {
    public static String limparCPF(String cpf){
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        return cpfLimpo;
    }
}
