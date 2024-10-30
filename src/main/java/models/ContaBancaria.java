package models;

import java.util.Date;

public abstract class ContaBancaria
{
    private int     id;
    private int     codigoBanco;
    private int     numAgencia;
    private int     numConta;
    private double  saldo;
    private Date    dataAbertura;
    private int     idTitular;
    
    public ContaBancaria(int id, int codigoBanco, int numAgencia, int numConta, double saldo, Date dataAbertura, int idTitular)
    {
        setId(id);
        setCodigoBanco(codigoBanco);
        setNumAgencia(numAgencia);
        setNumConta(numConta);
        this.saldo = 0;
        setDataAbertura(dataAbertura);
        setIdTitular(idTitular);
    }
    
    public void sacar(double valor) throws Exception
    {
        if (saldo < valor)
            throw new RuntimeException("Saldo insuficiente");

        saldo -= valor;
    }
    
    public void depositar(double valor) throws Exception
    {
        saldo += valor;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCodigoBanco()
    {
        return codigoBanco;
    }

    public void setCodigoBanco(int idBanco)
    {
        this.codigoBanco = idBanco;
    }

    public int getNumAgencia()
    {
        return numAgencia;
    }

    public void setNumAgencia(int numAgencia)
    {
        this.numAgencia = numAgencia;
    }

    public int getNumConta()
    {
        return numConta;
    }

    public void setNumConta(int numConta)
    {
        this.numConta = numConta;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public Date getDataAbertura()
    {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura)
    {
        this.dataAbertura = dataAbertura;
    }

    public int getIdTitular()
    {
        return idTitular;
    }

    public void setIdTitular(int idTitular)
    {
        this.idTitular = idTitular;
    }
}