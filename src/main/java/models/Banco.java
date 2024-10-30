package models;

public class Banco
{
    private int    codigo;
    private String nome;
    private String mascaraAgencia;
    private String mascaraConta;

    public Banco(int codigo, String nome, String mascaraAgencia, String mascaraConta)
    {
        setCodigo(codigo);
        setNome(nome);
        setMascaraAgencia(mascaraAgencia);
        setMascaraConta(mascaraConta);
    }

    public int getCodigo()
    {
        return codigo;
    }
    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }
    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public String getMascaraAgencia()
    {
        return mascaraAgencia;
    }
    public void setMascaraAgencia(String mascaraAgencia)
    {
        this.mascaraAgencia = mascaraAgencia;
    }
    public String getMascaraConta()
    {
        return mascaraConta;
    }
    public void setMascaraConta(String mascaraConta)
    {
        this.mascaraConta = mascaraConta;
    }
}