package br.edu.ifpb.teatro.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class RegraDePreco{

    private long id;
    private float valorHora;

    private DayOfWeek diaDaSemana;
    private String turno;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer mes;

    public RegraDePreco(float valorHora) {
        this.id = System.currentTimeMillis();
        this.valorHora = valorHora;
    }

    public long getId() { return id; }

    public float getValorHora() { return valorHora; }
    public void setValorHora(float valorHora) { this.valorHora = valorHora; }

    public DayOfWeek getDiaDaSemana() { return diaDaSemana; }
    public void setDiaDaSemana(DayOfWeek diaDaSemana) { this.diaDaSemana = diaDaSemana; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }
}