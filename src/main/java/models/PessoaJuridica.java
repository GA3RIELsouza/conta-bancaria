package models;

public class PessoaJuridica extends Pessoa
{
	private long   cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private long   inscrNacional;
	
	PessoaJuridica(long cnpj, String razaoSocial, String nomeFantasia, long inscricaoEstadual)
	{
		setCnpj(cnpj);
		setRazaoSocial(razaoSocial);
		setNomeFantasia(nomeFantasia);
		setInscrNacional(inscricaoEstadual);
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
	public long getInscrNacional()
	{
		return inscrNacional;
	}
	public void setInscrNacional(long inscrNacional)
	{
		this.inscrNacional = inscrNacional;
	}
}