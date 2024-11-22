package models;

import enums.TipoTel;
import utilities.NumeroTelefone;

public final class Telefone {
    private long    idPessoa;
    private long    id;
    private long    numero;
    private TipoTel tipo;

    public Telefone(){}

    public Telefone(long idPessoa, long id, String numero, TipoTel tipo) {
        setId(id);
        setIdPessoa(idPessoa);
        setNumero(numero);
        setTipo(tipo);
    }

    public long getIdPessoa() {
        return this.idPessoa;
    }

    public void setIdPessoa(long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return NumeroTelefone.aplicaMascara(Long.toString(numero));
    }

    public void setNumero(String numero) {
        if (!NumeroTelefone.isTelefone(numero))
            throw new RuntimeException(numero + " não é um número de telefone válido");

        this.numero = Long.parseLong(NumeroTelefone.removeMascara(numero));
    }

    public TipoTel getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoTel tipo) {
        this.tipo = tipo;
    }
}