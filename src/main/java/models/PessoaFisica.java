package models;

import enums.Situacao;
import java.sql.Date;
import enums.Sexo;
import utilities.Cpf;

public final class PessoaFisica extends Pessoa {
	private long   cpf;
	private String nome;
	private Date   dtNasc;
	private Sexo   sexo;

	public PessoaFisica(){}

	public PessoaFisica(long id, long idLocalidade, int numEndereco, String complementoEnd, Situacao situacao, String cpf, String nome, Date dataNasci, Sexo sexo) throws Exception {
		super(id, idLocalidade, numEndereco, complementoEnd, situacao);
		setCpf(cpf);
		setNome(nome);
		setDtNasc(dataNasci);
		setSexo(sexo);
	}
	
	public String getCpf() {
        return Cpf.aplicaMascara(Long.toString(cpf));
    }

    public void setCpf(String cpf) {
        if (!Cpf.isCpf(cpf))
            throw new RuntimeException(cpf + " não é um CPF válido");

        this.cpf = Long.parseLong(Cpf.removeMascara(cpf));
    }
	
    public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Date getDtNasc() {
		return this.dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	public Sexo getSexo() {
		return this.sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
}