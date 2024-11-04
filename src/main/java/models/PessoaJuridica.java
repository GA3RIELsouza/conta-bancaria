package models;

public final class PessoaJuridica extends Pessoa
{
	private long   cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private String inscrEstadual;
	
	PessoaJuridica(long cnpj, String razaoSocial, String nomeFantasia, String inscrEstadual)
	{
		setCnpj(cnpj);
		setRazaoSocial(razaoSocial);
		setNomeFantasia(nomeFantasia);
		setInscrEstadual(inscrEstadual);
	}
	
	public long getCnpj()
	{
		return cnpj;
	}
	public void setCnpj(long cpnj)
	{
		this.cnpj = cpnj;
	}
	public String getRazaoSocial()
	{
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial)
	{
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia()
	{
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia)
	{
		this.nomeFantasia = nomeFantasia;
	}
	public String getInscrEstadual()
	{
		return inscrEstadual;
	}
	public void setInscrEstadual(String inscrEstadual)
	{
		this.inscrEstadual = inscrEstadual;
	}
}