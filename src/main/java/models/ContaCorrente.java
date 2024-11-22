package models;

import java.sql.Date;

public class ContaCorrente extends ContaBancaria {
    private double valorCestaServicos;
    private double limitePixNoturno;

    public ContaCorrente(){}

    public ContaCorrente(long id, long codigoBanco, int numAgencia, long numConta, Date dataAbertura, long idTitular, double valorCestaServicos, double limitePixNoturno) {
        super(id, codigoBanco, numAgencia, numConta, dataAbertura, idTitular);
        setValorCestaServicos(valorCestaServicos);
        setLimitePixNoturno(limitePixNoturno);
    }

    public double getValorCestaServicos() {
        return this.valorCestaServicos;
    }

    public void setValorCestaServicos(double valorCestaServicos) {
        this.valorCestaServicos = valorCestaServicos;
    }

    public double getLimitePixNoturno() {
        return this.limitePixNoturno;
    }
    
    public void setLimitePixNoturno(double limitePixNoturno) {
        this.limitePixNoturno = limitePixNoturno;
    }
}
