package models;

import java.util.Date;

public final class MovimentacaoConta
{
    private ContaBancaria contaBancaria;
    private Date          data;
    private Evento        evento;
    private double        valor;

    public MovimentacaoConta(ContaBancaria contaBancaria, Date data, Evento evento, double valor)
    {
        setContaBancaria(contaBancaria);
        setData(data);
        setEvento(evento);
        setValor(valor);
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }
    public void setContaBancaria(ContaBancaria contaBancaria)
    {
        this.contaBancaria = contaBancaria;
    }
    public Date getData()
    {
        return data;
    }
    public void setData(Date data)
    {
        this.data = data;
    }
    public Evento getEvento()
    {
        return evento;
    }
    public void setEvento(Evento evento)
    {
        this.evento = evento;
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