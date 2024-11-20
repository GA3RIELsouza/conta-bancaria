package models;

import java.util.Date;
import java.util.List;

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

    public void gravar(){}

    public List<Movimentacao> listagem() {
        return null;
    }

    public long getContaBancaria() {
        return contaBancaria;
    }
    public void setContaBancaria(long contaBancaria) {
        this.contaBancaria = contaBancaria;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public long getEvento() {
        return evento;
    }
    public void setEvento(long evento) {
        this.evento = evento;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}