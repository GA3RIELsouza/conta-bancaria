package models;

import java.util.Date;

public final class ContaEspecial extends ContaCorrente {
    private double limiteCredito;
    private Date   dataVctoContrato;

    public ContaEspecial(int id, int codigoBanco, int numAgencia, int numConta, double saldo, Date dataAbertura, int idTitular, double valorCestaServicos, double limitePixNoturno, double limiteCredito, Date dataVctoContrato) {
        super(id, codigoBanco, numAgencia, numConta, saldo, dataAbertura, idTitular, valorCestaServicos, limitePixNoturno);
        setLimiteCredito(limiteCredito);
        setDataVctoContrato(dataVctoContrato);
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }
    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
    public Date getDataVctoContrato() {
        return dataVctoContrato;
    }
    public void setDataVctoContrato(Date dataVctoContrato) {
        this.dataVctoContrato = dataVctoContrato;
    }
}