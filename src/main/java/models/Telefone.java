package models;

public final class Telefone
{
    private int    id;
    private long   numero;
    private String tipo;

    public Telefone(int id, long numero, String tipo)
    {
        setId(id);
        setNumero(numero);
        setTipo(tipo);
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public long getNumero()
    {
        return numero;
    }
    public void setNumero(long numero)
    {
        this.numero = numero;
    }
    public String getTipo()
    {
        return tipo;
    }
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
}