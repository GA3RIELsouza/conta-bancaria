package models;

import java.util.Date;

public final class ContaPoupanca extends ContaBancaria
{
    private Date   diaAniversario;
    private int    indiceRemuneracao;
    private double percRedimentoReal;
    
    public ContaPoupanca(int id, int codigoBanco, int numAgencia, int numConta, double saldo, Date dataAbertura, int idTitular, Date diaAniversario, int indiceRemuneracao, double percRedimentoReal)
    {
        super(id, codigoBanco, numAgencia, numConta, saldo, dataAbertura, idTitular);
        setDiaAniversario(diaAniversario);
        setIndiceRemuneracao(indiceRemuneracao);
        setPercRedimentoReal(percRedimentoReal);
    }

    public Date getDiaAniversario()
    {
        return diaAniversario;
    }
    public void setDiaAniversario(Date diaAniversario)
    {
        this.diaAniversario = diaAniversario;
    }
    public int getIndiceRemuneracao()
    {
        return indiceRemuneracao;
    }
    public void setIndiceRemuneracao(int indiceRemuneracao)
    {
        this.indiceRemuneracao = indiceRemuneracao;
    }
    public double getPercRedimentoReal()
    {
        return percRedimentoReal;
    }
    public void setPercRedimentoReal(double percRedimentoReal)
    {
        this.percRedimentoReal = percRedimentoReal;
    }
}
