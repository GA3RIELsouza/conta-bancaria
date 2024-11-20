package models;

import java.util.Date;

public final class ContaSalario extends ContaBancaria {
    private long    cnpjVinculado;
    private double  limiteConsignado;
    private double  limiteAntecipadoMes;
    private boolean permiteAntecipar13o;
    private long    idContaVinculada;

    public ContaSalario(long id, long codigoBanco, int numAgencia, long numConta, double saldo, Date dataAbertura, long idTitular, long cnpjVinculado, double limiteConsignado, double limiteAntecipadoMes, boolean permiteAntecipar13o, long idContaVinculada) {
        super(id, codigoBanco, numAgencia, numConta, saldo, dataAbertura, idTitular);
        setCnpjVinculado(cnpjVinculado);
        setLimiteConsignado(limiteConsignado);
        setLimiteAntecipadoMes(limiteAntecipadoMes);
        setPermiteAntecipar13o(permiteAntecipar13o);
        setIdContaVinculada(idContaVinculada);
    }

    public long getCnpjVinculado() {
        return cnpjVinculado;
    }
    public void setCnpjVinculado(long cnpjVinculado) {
        this.cnpjVinculado = cnpjVinculado;
    }
    public double getLimiteConsignado() {
        return limiteConsignado;
    }
    public void setLimiteConsignado(double limiteConsignado) {
        this.limiteConsignado = limiteConsignado;
    }
    public double getLimiteAntecipadoMes() {
        return limiteAntecipadoMes;
    }
    public void setLimiteAntecipadoMes(double limiteAntecipadoMes) {
        this.limiteAntecipadoMes = limiteAntecipadoMes;
    }
    public boolean isPermiteAntecipar13o() {
        return permiteAntecipar13o;
    }
    public void setPermiteAntecipar13o(boolean permiteAntecipar13o) {
        this.permiteAntecipar13o = permiteAntecipar13o;
    }
    public long getIdContaVinculada() {
        return idContaVinculada;
    }
    public void setIdContaVinculada(long idContaVinculada) {
        this.idContaVinculada = idContaVinculada;
    }

    
}