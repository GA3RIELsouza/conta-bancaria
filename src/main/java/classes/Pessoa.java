package classes;

import interfaces.IPessoaDAO;
import utilities.Cpf;
import utilities.Telefone;

import java.util.List;
import java.util.ArrayList;

public final class Pessoa implements IPessoaDAO
{
    private long   id;
    private long   cpf;
    private String nome;
    private long   telefone;
    
    public Pessoa() {}
    
    public Pessoa(String cpf, String nome, String telefone) throws Exception
    {
        setCpf(cpf);
        setNome(nome);
        setTelefone(telefone);
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
        return new Pessoa();
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

    public String getCpf()
    {
        return Cpf.aplicaMascara(Long.toString(cpf));
    }

    public void setCpf(String cpf) throws Exception
    {
        if(!Cpf.isCpf(cpf))
            throw new Exception(cpf + " não é um CPF válido");

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

    public String getTelefone()
    {
        return Telefone.aplicaMascara(Long.toString(telefone));
    }

    public void setTelefone(String telefone) throws Exception
    {
        if(!Telefone.isTelefone(telefone))
            throw new Exception(telefone + " não é um número válido");

        this.telefone = Long.parseLong(Telefone.removeMascara(telefone));
    }
    
    @Override
    public String toString()
    {
        return "CPF      : " + getCpf()  + "\n" +
               "Nome     : " + getNome() + "\n" +
               "Telefone : " + getTelefone();
    }
}