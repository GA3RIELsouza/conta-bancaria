package models;

import utilities.Telefone;

public class TelefonePessoa
{
	//id
	private long numero;
	//tipo
	//situacao
	
	public String getNumero()
    {
        return Telefone.aplicaMascara(Long.toString(numero));
    }

    public void setNumero(String numero) throws Exception
    {
        if (!Telefone.isTelefone(numero))
            throw new Exception(numero + " não é um número válido");

        this.numero = Long.parseLong(Telefone.removeMascara(numero));
    }
}