package models;

import java.sql.Date;

import utilities.Cnpj;

public final class ContaSalario extends ContaBancaria {
    private long    cnpjVinculado;
    private double  limiteConsignado;
    private double  limiteAntecipadoMes;
    private boolean permiteAntecipar13o;
    private long    idContaVinculada;

    public ContaSalario(){}

    public ContaSalario(long id, long codigoBanco, long numAgencia, long numConta, Date dataAbertura, long idTitular, String cnpjVinculado, double limiteConsignado, double limiteAntecipadoMes, boolean permiteAntecipar13o, long idContaVinculada) {
        super(id, codigoBanco, numAgencia, numConta, dataAbertura, idTitular);
        setCnpjVinculado(cnpjVinculado);
        setLimiteConsignado(limiteConsignado);
        setLimiteAntecipadoMes(limiteAntecipadoMes);
        setPermiteAntecipar13o(permiteAntecipar13o);
        setIdContaVinculada(idContaVinculada);
    }

    public String getCnpjVinculado() {
        return Cnpj.aplicaMascara(Long.toString(cnpjVinculado));
    }

    public void setCnpjVinculado(String cnpjVinculado) {
        if (!Cnpj.isCnpj(cnpjVinculado))
            throw new RuntimeException(cnpjVinculado + " não é um CNPJ válido");

        this.cnpjVinculado = Long.parseLong(Cnpj.removeMascara(cnpjVinculado));
    }
    
    public double getLimiteConsignado() {
        return this.limiteConsignado;
    }

    public void setLimiteConsignado(double limiteConsignado) {
        this.limiteConsignado = limiteConsignado;
    }
    
    public double getLimiteAntecipadoMes() {
        return this.limiteAntecipadoMes;
    }

    public void setLimiteAntecipadoMes(double limiteAntecipadoMes) {
        this.limiteAntecipadoMes = limiteAntecipadoMes;
    }

    public boolean isPermiteAntecipar13o() {
        return this.permiteAntecipar13o;
    }

    public void setPermiteAntecipar13o(boolean permiteAntecipar13o) {
        this.permiteAntecipar13o = permiteAntecipar13o;
    }

    public long getIdContaVinculada() {
        return this.idContaVinculada;
    }

    public void setIdContaVinculada(long idContaVinculada) {
        this.idContaVinculada = idContaVinculada;
    }
}