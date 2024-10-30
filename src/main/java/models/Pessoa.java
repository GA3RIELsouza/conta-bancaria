package models;

import interfaces.IPessoaDAO;
import java.util.List;
import java.util.ArrayList;

public abstract class Pessoa implements IPessoaDAO
{
    private long   id;
    private long   cep;
    private long   numEndereco;
    private String complEndereco;
    private int    situacao;
    
    public Pessoa() {}
    
    public Pessoa(long cep, long numEnd, String complementoEnd, int situacao) throws Exception
    {
    	setCep(cep);
    	setNumEndereco(numEnd);
    	setComplEndereco(complementoEnd);
        setSituacao(situacao);
    }
    
    public Pessoa(long cep, long numEnd, int situacao) throws Exception
    {
    	setCep(cep);
    	setNumEndereco(numEnd);
        setSituacao(situacao);
    }

    public boolean inserir(Pessoa p)
    {
        return true;
    }
    public boolean atualizar(Pessoa p)
    {
        return true;
    }
    public boolean deletar(Pessoa p)
    {
        return true;
    }
    public boolean deletar(long id)
    {
        return true;
    }
    public Pessoa retornarPorId(long id)
    {
        return null;
    }
    public List<Pessoa> retornarTodos()
    {
        return new ArrayList<Pessoa>();
    }
    public long getId()
    {
        return id;
    }
    public void setId(long id)
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
	public long getNumEndereco()
	{
		return numEndereco;
	}
	public void setNumEndereco(long numEndereco)
	{
		this.numEndereco = numEndereco;
	}
	public String getComplEndereco()
	{
		return complEndereco;
	}
	public void setComplEndereco(String complEndereco)
	{
		this.complEndereco = complEndereco;
	}
	public int getSituacao()
	{
		return situacao;
	}
	public void setSituacao(int situacao)
	{
		this.situacao = situacao;
	}
}