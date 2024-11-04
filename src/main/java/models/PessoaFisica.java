package models;

import java.util.Date;
import java.util.List;

import enums.Situacao;
import utilities.Cpf;

public final class PessoaFisica extends Pessoa
{
	private long   cpf;
	private String nome;
	private Date   dtNasc;
	private int    sexo;

	PessoaFisica(long id, long cep, int numEndereco, String complementoEnd, Situacao situacao, String cpf, String nome, Date dataNasci, int sexo) throws Exception
	{
		super(id, cep, numEndereco, complementoEnd, situacao);
		setCpf(cpf);
		setNome(nome);
		setDtNasc(dataNasci);
		setSexo(sexo);
	}

	public PessoaFisica ler()
    {
        return null;
    }

    public void gravar(){}

    public List<Pessoa> listagem()
    {
        return null;
    }
	
	public String getCpf()
    {
        return Cpf.aplicaMascara(Long.toString(cpf));
    }
    public void setCpf(String cpf) throws Exception
    {
        if (!Cpf.isCpf(cpf))
            throw new RuntimeException(cpf + " não é um CPF válido");

        this.cpf = Long.parseLong(Cpf.removeMascara(cpf));
    }
    public String getNome()
    {
		return nome;
	}
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	public Date getDtNasc()
	{
		return dtNasc;
	}
	public void setDtNasc(Date dtNasc)
	{
		this.dtNasc = dtNasc;
	}
	public int getSexo()
	{
		return sexo;
	}
	public void setSexo(int sexo)
	{
		this.sexo = sexo;
	}
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
}