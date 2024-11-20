package models;

import enums.Situacao;

public abstract class Pessoa {
    private long     id;
    private long     cep;
    private int      numEndereco;
    private String   complEndereco;
    private Situacao situacao;
    
    public Pessoa() {}
    
    public Pessoa(long id, long cep, int numEndereco, String complementoEnd, Situacao situacao) throws Exception {
        setId(id);
    	setCep(cep);
    	setNumEndereco(numEndereco);
    	setComplEndereco(complementoEnd);
        setSituacao(situacao);
    }
    
    public Pessoa(long id, long cep, int numEndereco, Situacao situacao) throws Exception {
        setId(id);
    	setCep(cep);
    	setNumEndereco(numEndereco);
        setSituacao(situacao);
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

	public int getNumEndereco() {
		return this.numEndereco;
	}

	public void setNumEndereco(int numEndereco) {
		this.numEndereco = numEndereco;
	}

	public String getComplEndereco() {
		return this.complEndereco;
	}

	public void setComplEndereco(String complEndereco) {
		this.complEndereco = complEndereco;
	}

	public Situacao getSituacao() {
		return this.situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
}