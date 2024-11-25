package models;

import java.sql.Date;

public final class ContaPoupanca extends ContaBancaria {
    private long   idIndiceRemuneracao;
    private double percRedimentoReal;

    public ContaPoupanca(){}
    
    public ContaPoupanca(long id, long codigoBanco, long numAgencia, int numConta, Date dataAbertura, long idTitular, long idIndiceRemuneracao, double percRedimentoReal) {
        super(id, codigoBanco, numAgencia, numConta, dataAbertura, idTitular);
        setIdIndiceRemuneracao(idIndiceRemuneracao);
        setPercRedimentoReal(percRedimentoReal);
    }

    public long getIdIndiceRemuneracao() {
        return this.idIndiceRemuneracao;
    }
    public void setIdIndiceRemuneracao(long idIndiceRemuneracao) {
        this.idIndiceRemuneracao = idIndiceRemuneracao;
    }
    public double getPercRedimentoReal() {
        return this.percRedimentoReal;
    }
    public void setPercRedimentoReal(double percRedimentoReal) {
        this.percRedimentoReal = percRedimentoReal;
    }
}
