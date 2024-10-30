package models;

import enums.TipoMov;

public final class Evento
{
    private int     id;
    private String  descricao;
    private TipoMov tipoMovimentacao;
    private String  situacao;

    public Evento(int id, String descricao, TipoMov tipoMovimentacao, String situacao)
    {
        setId(id);
        setDescricao(descricao);
        setTipoMovimentacao(tipoMovimentacao);
        setSituacao(situacao);
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getDescricao()
    {
        return descricao;
    }
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
    public TipoMov getTipoMovimentacao()
    {
        return tipoMovimentacao;
    }
    public void setTipoMovimentacao(TipoMov tipoMovimentacao)
    {
        this.tipoMovimentacao = tipoMovimentacao;
    }
    public String getSituacao()
    {
        return situacao;
    }
    public void setSituacao(String situacao)
    {
        this.situacao = situacao;
    }
}