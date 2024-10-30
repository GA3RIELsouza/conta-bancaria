package models;

import java.util.Date;

public final class ContaSalario extends ContaBancaria
{
    private double  cnpjVinculado;
    private double  limiteConsignado;
    private double  limiteAntecipadoMes;
    private boolean permiteAntecipar13o;
    private int     idContaVinculada;

    public ContaSalario(int id, int codigoBanco, int numAgencia, int numConta, double saldo, Date dataAbertura, int idTitular, double cnpjVinculado, double limiteConsignado, double limiteAntecipadoMes, boolean permiteAntecipar13o, int idContaVinculada)
    {
        super(id, codigoBanco, numAgencia, numConta, saldo, dataAbertura, idTitular);
        setCnpjVinculado(cnpjVinculado);
        setLimiteConsignado(limiteConsignado);
        setLimiteAntecipadoMes(limiteAntecipadoMes);
        setPermiteAntecipar13o(permiteAntecipar13o);
        setIdContaVinculada(idContaVinculada);
    }

    public double getCnpjVinculado()
    {
        return cnpjVinculado;
    }
    public void setCnpjVinculado(double cnpjVinculado)
    {
        this.cnpjVinculado = cnpjVinculado;
    }
    public double getLimiteConsignado()
    {
        return limiteConsignado;
    }
    public void setLimiteConsignado(double limiteConsignado)
    {
        this.limiteConsignado = limiteConsignado;
    }
    public double getLimiteAntecipadoMes()
    {
        return limiteAntecipadoMes;
    }
    public void setLimiteAntecipadoMes(double limiteAntecipadoMes)
    {
        this.limiteAntecipadoMes = limiteAntecipadoMes;
    }
    public boolean isPermiteAntecipar13o()
    {
        return permiteAntecipar13o;
    }
    public void setPermiteAntecipar13o(boolean permiteAntecipar13o)
    {
        this.permiteAntecipar13o = permiteAntecipar13o;
    }
    public int getIdContaVinculada()
    {
        return idContaVinculada;
    }
    public void setIdContaVinculada(int idContaVinculada)
    {
        this.idContaVinculada = idContaVinculada;
    }

    
}