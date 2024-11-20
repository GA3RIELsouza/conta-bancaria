package models;

import java.util.Date;

public final class Movimentacao {
    private long   contaBancaria;
    private Date   data;
    private long   evento;
    private double valor;

    public Movimentacao(long contaBancaria, Date data, long evento, double valor) {
        setContaBancaria(contaBancaria);
        setData(data);
        setEvento(evento);
        setValor(valor);
    }

    public long getContaBancaria() {
        return this.contaBancaria;
    }

    public void setContaBancaria(long contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public long getEvento() {
        return this.evento;
    }

    public void setEvento(long evento) {
        this.evento = evento;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}