package models;

import utilities.Cpf;
import java.util.Date;

public final class PessoaFisica extends Pessoa
{
	private long   cpf;
	private String nome;
	private Date   dtNasc;
	private int    sexo;

	PessoaFisica(String cpf, String nome, Date dataNasci, int sexo) throws Exception
	{
		setCpf(cpf);
		setNome(nome);
		setDtNasc(dataNasci);
		setSexo(sexo);
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