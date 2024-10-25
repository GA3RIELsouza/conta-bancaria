package models;

import enums.TipoOper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class EventoConta extends Evento
{
    private LocalDateTime data;
    private double        saldo;
    private double        vlrMovimentado;
    
    public EventoConta(int id, String descricao, TipoOper tipo, LocalDateTime data, double saldo, double vlrMovimentado)
    {
        setId(id);
        setDescricao(descricao);
        setTipo(tipo);
        setData(data);
        setSaldo(saldo);
        setVlrMovimentado(vlrMovimentado);
    }
    
    @Override
    public String toString()
    {
        return "ID        : "    + getId()        + "\n" +
               "Descrição : "    + getDescricao() + "\n" +
               "Tipo      : "    + getTipo()      + "\n" +
               "Data      : "    + getData()      + "\n" +
               "Saldo     : R$ " + getSaldo()     + "\n" +
               "Vlr. mov. : R$ " + getVlrMovimentado();
    }

    public String getData()
    {
        return data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm"));
    }

    public void setData(LocalDateTime data)
    {
        this.data = data;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public void setSaldo(double saldo)
    {
        this.saldo = saldo;
    }

    public double getVlrMovimentado()
    {
        return vlrMovimentado;
    }

    public void setVlrMovimentado(double vlrMovimentado)
    {
        this.vlrMovimentado = vlrMovimentado;
    }
}