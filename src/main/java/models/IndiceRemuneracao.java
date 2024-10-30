package models;

public final class IndiceRemuneracao
{
    private int    codigo;
    private String descricao;
    private int    periodicidade;
    private String situacao;

    public IndiceRemuneracao(int codigo, String descricao, int periodicidade, String situacao)
    {
        setCodigo(codigo);
        setDescricao(descricao);
        setPeriodicidade(periodicidade);
        setSituacao(situacao);
    }

    public int getCodigo()
    {
        return codigo;
    }
    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }
    public String getDescricao()
    {
        return descricao;
    }
    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
    public int getPeriodicidade()
    {
        return periodicidade;
    }
    public void setPeriodicidade(int periodicidade)
    {
        this.periodicidade = periodicidade;
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