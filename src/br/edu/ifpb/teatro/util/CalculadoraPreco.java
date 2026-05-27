package br.edu.ifpb.teatro.util;

import br.edu.ifpb.teatro.model.RegraDePreco;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CalculadoraPreco {

    public static float recuperarPrecoVigente(List<RegraDePreco> regras, LocalDateTime dataHoraDesejada) {

        float precoVencedor = 10.0f;

        LocalTime horaAluguel = dataHoraDesejada.toLocalTime();
        int mesAluguel = dataHoraDesejada.getMonthValue();

        String turnoAtual = "Fora de Turno";
        if (!horaAluguel.isBefore(LocalTime.of(8, 0)) && horaAluguel.isBefore(LocalTime.of(13, 0))) {
            turnoAtual = "Manhã (08-12)";
        } else if (!horaAluguel.isBefore(LocalTime.of(13, 0)) && horaAluguel.isBefore(LocalTime.of(19, 0))) {
            turnoAtual = "Tarde (13-18)";
        } else if (!horaAluguel.isBefore(LocalTime.of(19, 0)) && !horaAluguel.isAfter(LocalTime.of(23, 59))) {
            turnoAtual = "Noite (19-23)";
        }

        for (RegraDePreco regra : regras) {

            boolean atendeMes = (regra.getMes() == null || regra.getMes() == mesAluguel);

            boolean atendeDia = (regra.getDiaDaSemana() == null ||
                    regra.getDiaDaSemana().equals(dataHoraDesejada.getDayOfWeek()));

            boolean atendeTurno = (regra.getTurno() == null ||
                    regra.getTurno().equals("Qualquer Turno") ||
                    regra.getTurno().equals(turnoAtual));

            boolean atendeHora = false;
            if (regra.getHoraInicio() == null || regra.getHoraFim() == null) {
                atendeHora = true;
            } else {
                boolean jaComecou = horaAluguel.isAfter(regra.getHoraInicio()) ||
                        horaAluguel.equals(regra.getHoraInicio());

                boolean naoAcabou = horaAluguel.isBefore(regra.getHoraFim()) ||
                        horaAluguel.equals(regra.getHoraFim());

                atendeHora = jaComecou && naoAcabou;
            }

            if (atendeMes && atendeDia && atendeTurno && atendeHora) {
                if (regra.getValorHora() > precoVencedor) {
                    precoVencedor = regra.getValorHora();
                }
            }
        }

        return precoVencedor;
    }
}