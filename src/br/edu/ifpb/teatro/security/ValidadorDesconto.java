package br.edu.ifpb.teatro.security;

import br.edu.ifpb.teatro.model.Pessoa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidadorDesconto {
    public static float calcularDescontoAniversario(Pessoa cliente, float valorOriginal) {

        if (cliente == null || cliente.getDataNascimento() == null || cliente.getDataNascimento().isEmpty()) {
            return valorOriginal;
        }

        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(cliente.getDataNascimento(), formato);
            LocalDate dataHoje = LocalDate.now();

            boolean isMesmoDia = dataNascimento.getDayOfMonth() == dataHoje.getDayOfMonth();
            boolean isMesmoMes = dataNascimento.getMonth() == dataHoje.getMonth();

            if (isMesmoDia && isMesmoMes) {
                return valorOriginal * 0.90f;
            }

        } catch (Exception e) {

            System.out.println("formato de data inválido para verificação de desconto.");
        }

        return valorOriginal;
    }

}
