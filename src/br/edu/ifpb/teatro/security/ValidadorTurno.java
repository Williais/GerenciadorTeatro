package br.edu.ifpb.teatro.security;

import java.time.LocalTime;

public class ValidadorTurno {
    public static String validarTurno(LocalTime inicio){

        if (!inicio.isBefore(LocalTime.of(8, 0)) && inicio.isBefore(LocalTime.of(13, 0))){
            return "Manhã";
        } else if (!inicio.isBefore(LocalTime.of(13, 0)) && inicio.isBefore(LocalTime.of(19, 0))){
            return "Tarde";
        } else if (!inicio.isBefore(LocalTime.of(19, 0)) && !inicio.isAfter(LocalTime.of(23, 59))){
            return "Noite";
        } else{
            return "Turno Invalido";}
    }
}
