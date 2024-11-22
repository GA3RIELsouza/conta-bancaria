package models;

import java.sql.Date;

public final class ContaEspecial extends ContaCorrente {
    private double limiteCredito;
    private Date   dataVctoContrato;

    public ContaEspecial(int id, int codigoBanco, int numAgencia, int numConta, Date dataAbertura, int idTitular, double valorCestaServicos, double limitePixNoturno, double limiteCredito, Date dataVctoContrato) {
        super(id, codigoBanco, numAgencia, numConta, dataAbertura, idTitular, valorCestaServicos, limitePixNoturno);
        setLimiteCredito(limiteCredito);
        setDataVctoContrato(dataVctoContrato);
    }

    public double getLimiteCredito() {
        return this.limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Date getDataVctoContrato() {
        return this.dataVctoContrato;
    }

    public void setDataVctoContrato(Date dataVctoContrato) {
        this.dataVctoContrato = dataVctoContrato;
    }
}
