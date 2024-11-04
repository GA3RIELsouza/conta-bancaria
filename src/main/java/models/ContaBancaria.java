package models;

import java.util.Date;

public abstract class ContaBancaria
{
    private long    id;
    private long    codigoBanco;
    private int     numAgencia;
    private long    numConta;
    private double  saldo;
    private Date    dataAbertura;
    private long    idTitular;
    
    public ContaBancaria(long id, long codigoBanco, int numAgencia, long numConta, double saldo, Date dataAbertura, long idTitular)
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

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getCodigoBanco()
    {
        return codigoBanco;
    }

    public void setCodigoBanco(long idBanco)
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

    public long getNumConta()
    {
        return numConta;
    }

    public void setNumConta(long numConta)
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

    public long getIdTitular()
    {
        return idTitular;
    }

    public void setIdTitular(long idTitular)
    {
        this.idTitular = idTitular;
    }
}