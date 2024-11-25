package models;

import java.sql.Date;

public final class Movimentacao {
    private long   id;
    private long   idContaBancaria;
    private Date   dataMovimentacao;
    private long   idEvento;
    private double valor;

    public Movimentacao(){}

    public Movimentacao(long id, long idContaBancaria, Date dataMovimentacao, long idEvento, double valor) {
        setId(id);
        setIdContaBancaria(idContaBancaria);
        setDataMovimentacao(dataMovimentacao);
        setIdEvento(idEvento);
        setValor(valor);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdContaBancaria() {
        return this.idContaBancaria;
    }

    public void setIdContaBancaria(long idContaBancaria) {
        this.idContaBancaria = idContaBancaria;
    }

    public Date getDataMovimentacao() {
        return this.dataMovimentacao;
    }

    public void setDataMovimentacao(Date dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public long getIdEvento() {
        return this.idEvento;
    }

    public void setIdEvento(long idEvento) {
        this.idEvento = idEvento;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}