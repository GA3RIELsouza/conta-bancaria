package models;

import enums.Situacao;

public final class IndiceRemuneracao {
    private long     id;
    private String   descricao;
    private int      periodicidade;
    private Situacao situacao;

    public IndiceRemuneracao(){}

    public IndiceRemuneracao(long id, String descricao, int periodicidade, Situacao situacao) {
        setId(id);
        setDescricao(descricao);
        setPeriodicidade(periodicidade);
        setSituacao(situacao);
    }

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPeriodicidade() {
        return this.periodicidade;
    }

    public void setPeriodicidade(int periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Situacao getSituacao() {
        return this.situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}