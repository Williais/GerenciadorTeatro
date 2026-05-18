package br.edu.ifpb.teatro.util;

import java.util.Random;

public class GeradorDeCodigo {

    public static String gerar() {
        Random random = new Random();
        int numero = random.nextInt(999999);
        return String.format("%06d", numero);
    }
}