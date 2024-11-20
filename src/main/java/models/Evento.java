package models;

import enums.Situacao;
import enums.TipoMov;

public final class Evento {
    private long     id;
    private String   descricao;
    private TipoMov  tipoMovimentacao;
    private Situacao situacao;

    public Evento(long id, String descricao, TipoMov tipoMovimentacao, Situacao situacao) {
        setId(id);
        setDescricao(descricao);
        setTipoMovimentacao(tipoMovimentacao);
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

    public TipoMov getTipoMovimentacao() {
        return this.tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMov tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public Situacao getSituacao() {
        return this.situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}