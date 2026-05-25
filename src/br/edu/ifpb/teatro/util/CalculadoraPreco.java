package br.edu.ifpb.teatro.util;

import br.edu.ifpb.teatro.model.RegraDePreco;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class CalculadoraPreco {


     //Este metodo recebe a lista de todas as regras e a data que queremos geralmente a cada hora em especifico
     //Ele serve para definir o PREÇO DA HORA VIGENTE.

    public static float recuperarPrecoVigente(List<RegraDePreco> regras, LocalDateTime dataHoraDesejada) {

        //o preço é 10.00 por padrao
        float precoVencedor = 10.0f;

        // pegando apenas a hora pra facilitar na hora de comparar
        LocalTime horaAluguel = dataHoraDesejada.toLocalTime();

        // olhar regra por regra da lista
        for (RegraDePreco regra : regras) {


            // Se a o dia da semana da regra for null é pq serve para todos os dias dai passa
            boolean atendeDia = (regra.getDiaDaSemana() == null ||
                    regra.getDiaDaSemana().equals(dataHoraDesejada.getDayOfWeek()));

            // teste de hora
            boolean atendeHora = false;
            if (regra.getHoraInicio() == null || regra.getHoraFim() == null) {
                atendeHora = true; // Se a regra não tem hora definida ela vale o dia completo
            } else {
                // A hora desejada depois do início? OU é a hora de início?
                boolean jaComecou = horaAluguel.isAfter(regra.getHoraInicio()) ||
                        horaAluguel.equals(regra.getHoraInicio());

                // A hora desejada é antes do fim da hora da regra? OU é a hora de fim?
                boolean naoAcabou = horaAluguel.isBefore(regra.getHoraFim()) ||
                        horaAluguel.equals(regra.getHoraFim());

                // Se der certo aqui com true esta dentro do intervalo de horario da regra
                atendeHora = jaComecou && naoAcabou;
            }

            // A REGRA DO MAIOR
            if (atendeDia && atendeHora) {
                // Se o valor dela for maior que o atualé atualizado
                if (regra.getValorHora() > precoVencedor) {
                    precoVencedor = regra.getValorHora();
                }
            }
        }

        // 3. O RESULTADO: O valor que sair daqui é o preço de UMA HORA de aluguel para aquela hora em especifico
        //  vou ter que criar um laço que faça a calculadoradepreco para cada hora dentro do periodo desejado e some uma por uma na hora de
        //devolver o valor total do contrato, esse laço ficara no gerador de contrato provavelmente
        return precoVencedor;
    }
}