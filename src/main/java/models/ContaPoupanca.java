package models;

import java.util.Date;

public final class ContaPoupanca extends ContaBancaria {
    private int    diaAniversario;
    private long   indiceRemuneracao;
    private double percRedimentoReal;
    
    public ContaPoupanca(int id, int codigoBanco, int numAgencia, int numConta, double saldo, Date dataAbertura, long idTitular, int diaAniversario, long indiceRemuneracao, double percRedimentoReal) {
        super(id, codigoBanco, numAgencia, numConta, saldo, dataAbertura, idTitular);
        setDiaAniversario(diaAniversario);
        setIndiceRemuneracao(indiceRemuneracao);
        setPercRedimentoReal(percRedimentoReal);
    }

    public int getDiaAniversario() {
        return diaAniversario;
    }
    public void setDiaAniversario(int diaAniversario) {
        this.diaAniversario = diaAniversario;
    }
    public long getIndiceRemuneracao() {
        return indiceRemuneracao;
    }
    public void setIndiceRemuneracao(long indiceRemuneracao) {
        this.indiceRemuneracao = indiceRemuneracao;
    }
    public double getPercRedimentoReal() {
        return percRedimentoReal;
    }
    public void setPercRedimentoReal(double percRedimentoReal) {
        this.percRedimentoReal = percRedimentoReal;
    }
}
