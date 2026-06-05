package br.edu.ifpb.teatro.util;

import java.time.DayOfWeek;

public class ConversaoDiaSemana {

    public static DayOfWeek converterDia(String dia) {
        switch (dia) {
            case "Segunda": return DayOfWeek.MONDAY;
            case "Terça":   return DayOfWeek.TUESDAY;
            case "Quarta":  return DayOfWeek.WEDNESDAY;
            case "Quinta":  return DayOfWeek.THURSDAY;
            case "Sexta":   return DayOfWeek.FRIDAY;
            case "Sábado":  return DayOfWeek.SATURDAY;
            case "Domingo": return DayOfWeek.SUNDAY;
            default: return null;
        }
    }
}
