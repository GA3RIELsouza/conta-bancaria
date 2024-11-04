package models;

public class Banco
{
    private long   codigo;
    private String nome;
    private String mascaraAgencia;
    private String mascaraConta;

    public Banco(long codigo, String nome, String mascaraAgencia, String mascaraConta)
    {
        setCodigo(codigo);
        setNome(nome);
        setMascaraAgencia(mascaraAgencia);
        setMascaraConta(mascaraConta);
    }

    public long getCodigo()
    {
        return codigo;
    }
    public void setCodigo(long codigo)
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