package models;

import java.util.Date;

public final class ContaPoupanca extends ContaBancaria {
    private int    diaAniversario;
    private long   indiceRemuneracao;
    private double percRedimentoReal;
    
    public ContaPoupanca(int id, int codigoBanco, int numAgencia, int numConta, Date dataAbertura, long idTitular, int diaAniversario, long indiceRemuneracao, double percRedimentoReal) {
        super(id, codigoBanco, numAgencia, numConta, dataAbertura, idTitular);
        setDiaAniversario(diaAniversario);
        setIndiceRemuneracao(indiceRemuneracao);
        setPercRedimentoReal(percRedimentoReal);
    }

    public int getDiaAniversario() {
        return this.diaAniversario;
    }
    public void setDiaAniversario(int diaAniversario) {
        this.diaAniversario = diaAniversario;
    }
    public long getIndiceRemuneracao() {
        return this.indiceRemuneracao;
    }
    public void setIndiceRemuneracao(long indiceRemuneracao) {
        this.indiceRemuneracao = indiceRemuneracao;
    }
    public double getPercRedimentoReal() {
        return this.percRedimentoReal;
    }
    public void setPercRedimentoReal(double percRedimentoReal) {
        this.percRedimentoReal = percRedimentoReal;
    }
}
