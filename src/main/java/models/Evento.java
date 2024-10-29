package models;

import enums.TipoOper;

public abstract class Evento
{
    private int      id;
    private String   descricao;
    private TipoOper tipo;
    private boolean  ativo;
    
    public Evento()
    {
        setAtivo(true);
    }
    
    public final void desativar() throws Exception
    {
        if (!isAtivo())
            throw new Exception("O evento já está inativo");

        setAtivo(false);
    }

    public final int getId()
    {
        return id;
    }

    public final void setId(int id)
    {
        this.id = id;
    }

    public final String getDescricao()
    {
        return descricao;
    }

    public final void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public final TipoOper getTipo()
    {
        return tipo;
    }

    public final void setTipo(TipoOper tipo)
    {
        this.tipo = tipo;
    }

    public final boolean isAtivo()
    {
        return ativo;
    }

    public final void setAtivo(boolean ativo)
    {
        this.ativo = ativo;
    }
}