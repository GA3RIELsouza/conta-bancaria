package classes;

public final class ContaCorrente extends ContaBancaria
{
    public ContaCorrente()
    {
        super();
    }

    public ContaCorrente(String cpf, int numBanco, int numAgencia, int numConta) throws Exception
    {
        super(cpf, numBanco, numAgencia, numConta);
    }
}