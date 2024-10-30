package models;

import enums.Estado;

public class Localidade
{
    private int    id;
    private long   cep;
    private Estado estado;
    private String cidade;
    private String bairro;
    private String logradouro;

    public Localidade(int id, long cep, Estado estado, String cidade, String bairro, String logradouro)
    {
        setId(id);
        setCep(cep);
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setLogradouro(logradouro);
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public long getCep()
    {
        return cep;
    }
    public void setCep(long cep)
    {
        this.cep = cep;
    }
    public Estado getEstado()
    {
        return estado;
    }
    public void setEstado(Estado estado)
    {
        this.estado = estado;
    }
    public String getCidade()
    {
        return cidade;
    }
    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }
    public String getBairro()
    {
        return bairro;
    }
    public void setBairro(String bairro)
    {
        this.bairro = bairro;
    }
    public String getLogradouro()
    {
        return logradouro;
    }
    public void setLogradouro(String logradouro)
    {
        this.logradouro = logradouro;
    }
}