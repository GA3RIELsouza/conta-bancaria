package models;

import java.util.Date;

public final class Cotacao
{
    private int    codigo;
    private Date   data;
    private double valor;

    public Cotacao(int codigo, Date data, double valor)
    {
        setCodigo(codigo);
        setData(data);
        setValor(valor);
    }

    public int getCodigo()
    {
        return codigo;
    }
    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }
    public Date getData()
    {
        return data;
    }
    public void setData(Date data)
    {
        this.data = data;
    }
    public double getValor()
    {
        return valor;
    }
    public void setValor(double valor)
    {
        this.valor = valor;
    }
}