package models;

import enums.Estado;
import utilities.Cep;

public class Localidade {
    private long   id;
    private long   cep;
    private Estado estado;
    private String cidade;
    private String bairro;
    private String logradouro;

    public Localidade(){}

    public Localidade(long id, long cep, Estado estado, String cidade, String bairro, String logradouro) {
        setId(id);
        setCep(cep);
        setEstado(estado);
        setCidade(cidade);
        setBairro(bairro);
        setLogradouro(logradouro);
    }

    public Localidade(long id, long cep) {
        Localidade localidadeBuscaCep = Cep.buscarDadosCep(cep);

        setId(id);
        setCep(cep);
        setEstado(localidadeBuscaCep.getEstado());
        setCidade(localidadeBuscaCep.getCidade());
        setBairro(localidadeBuscaCep.getBairro());
        setLogradouro(localidadeBuscaCep.getLogradouro());
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCep() {
        return this.cep;
    }

    public void setCep(long cep) {
        this.cep = cep;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
}
