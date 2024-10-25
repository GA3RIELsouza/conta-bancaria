package models;

public final class ContaPoupanca extends ContaBancaria
{
    private int diaRendimento;
    
    public ContaPoupanca()
    {
        super();
    }
    
    public ContaPoupanca(String cpf, int numBanco, int numAgencia, int numConta) throws Exception
    {
        super(cpf, numBanco, numAgencia, numConta);
    }
    
    public void calcularRendimentoMensal(double taxaRendimento) throws Exception
    {
        double vlrRendimento;
        
        if(!isAtiva())
            throw new Exception("Não é possível movimentar contas inativas");

        if(taxaRendimento <= 0)
            throw new Exception(taxaRendimento + " não é uma taxa válida");

        vlrRendimento = getSaldo() * ( taxaRendimento / 100 );
        depositar(vlrRendimento);
    }

    public int getDiaRendimento()
    {
        return diaRendimento;
    }

    public void setDiaRendimento(int diaRendimento) throws Exception
    {
        if(diaRendimento < 1 || diaRendimento > 31)
            throw new Exception(diaRendimento + " não é um dia válido");

        this.diaRendimento = diaRendimento;
    }
}
