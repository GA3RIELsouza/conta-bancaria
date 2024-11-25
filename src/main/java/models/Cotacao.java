package models;

import java.sql.Date;

public final class Cotacao {
    private long   id;
    private long   idIndiceRemuneracao;
    private Date   dataCotacao;
    private double valor;

    public Cotacao(){}

    public Cotacao(long id, long idIndiceRemuneracao, Date dataCotacao, double valor) {
        setId(id);
        setIdIndiceRemuneracao(idIndiceRemuneracao);
        setDataCotacao(dataCotacao);
        setValor(valor);
    }

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public long getIdIndiceRemuneracao() {
        return this.idIndiceRemuneracao;
    }
    
    public void setIdIndiceRemuneracao(long idIndiceRemuneracao) {
        this.idIndiceRemuneracao = idIndiceRemuneracao;
    }

    public Date getDataCotacao() {
        return this.dataCotacao;
    }

    public void setDataCotacao(Date dataCotacao) {
        this.dataCotacao = dataCotacao;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}