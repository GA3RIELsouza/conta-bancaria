package models;

import enums.Situacao;
import interfaces.IPessoaDAO;
import java.util.List;
import java.util.ArrayList;

public abstract class Pessoa implements IPessoaDAO {
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

    public Pessoa ler() {
        return null;
    }

    public void gravar(){}

    public void desabilitar(){}

    public List<Pessoa> listagem() {
        return null;
    }

    public void adicionarFone(){}

    public void removerFone(){}

    public boolean inserir(Pessoa p) {
        return true;
    }
    public boolean atualizar(Pessoa p) {
        return true;
    }
    public boolean deletar(Pessoa p) {
        return true;
    }
    public boolean deletar(long id) {
        return true;
    }
    public Pessoa retornarPorId(long id) {
        return null;
    }
    public List<Pessoa> retornarTodos() {
        return new ArrayList<Pessoa>();
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getCep() {
		return cep;
	}
	public void setCep(long cep)
	{
		this.cep = cep;
	}
	public int getNumEndereco()
	{
		return numEndereco;
	}
	public void setNumEndereco(int numEndereco)
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
	public Situacao getSituacao()
	{
		return situacao;
	}
	public void setSituacao(Situacao situacao)
	{
		this.situacao = situacao;
	}
}