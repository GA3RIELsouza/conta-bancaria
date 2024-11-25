package models;

import enums.Situacao;
import utilities.Cnpj;

public final class PessoaJuridica extends Pessoa {
	private long   cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private long   inscrEstadual;
	
	public PessoaJuridica(){}

	public PessoaJuridica(long id, long idLocalidade, int numEndereco, String complementoEnd, Situacao situacao, String cnpj, String razaoSocial, String nomeFantasia, long inscrEstadual) throws Exception {
		super(id, idLocalidade, numEndereco, complementoEnd, situacao);
		setCnpj(cnpj);
		setRazaoSocial(razaoSocial);
		setNomeFantasia(nomeFantasia);
		setInscrEstadual(inscrEstadual);
	}
	
	public String getCnpj() {
        return Cnpj.aplicaMascara(Long.toString(cnpj));
    }

    public void setCnpj(String cnpj) {
        if (!Cnpj.isCnpj(cnpj))
            throw new RuntimeException(cnpj + " não é um CNPJ válido");

        this.cnpj = Long.parseLong(Cnpj.removeMascara(cnpj));
    }

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public long getInscrEstadual() {
		return this.inscrEstadual;
	}

	public void setInscrEstadual(long inscrEstadual) {
		this.inscrEstadual = inscrEstadual;
	}
}