package models;

import java.util.Date;

public class ContaCorrente extends ContaBancaria
{
    private double valorCestaServicos;
    private double limitePixNoturno;

    public ContaCorrente(int id, int codigoBanco, int numAgencia, int numConta, double saldo, Date dataAbertura, int idTitular, double valorCestaServicos, double limitePixNoturno)
    {
        super(id, codigoBanco, numAgencia, numConta, saldo, dataAbertura, idTitular);
        setValorCestaServicos(valorCestaServicos);
        setLimitePixNoturno(limitePixNoturno);
    }

    public double getValorCestaServicos()
    {
        return valorCestaServicos;
    }
    public void setValorCestaServicos(double valorCestaServicos)
    {
        this.valorCestaServicos = valorCestaServicos;
    }
    public double getLimitePixNoturno()
    {
        return limitePixNoturno;
    }
    public void setLimitePixNoturno(double limitePixNoturno)
    {
        this.limitePixNoturno = limitePixNoturno;
    }
}