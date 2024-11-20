package models;

import java.util.Date;

public final class Cotacao {
    private long   codigo;
    private Date   data;
    private double valor;

    public Cotacao(long codigo, Date data, double valor) {
        setCodigo(codigo);
        setData(data);
        setValor(valor);
    }

    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}