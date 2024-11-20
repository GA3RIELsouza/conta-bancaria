package models;

public final class IndiceRemuneracao {
    private long   codigo;
    private String descricao;
    private int    periodicidade;
    private int    situacao;

    public IndiceRemuneracao(long codigo, String descricao, int periodicidade, int situacao) {
        setCodigo(codigo);
        setDescricao(descricao);
        setPeriodicidade(periodicidade);
        setSituacao(situacao);
    }

    public long getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(long codigo) {
        this.codigo = codigo;
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

    public int getSituacao() {
        return this.situacao;
    }

    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }
}