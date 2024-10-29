package models;

import enums.TipoOper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import utilities.Cpf;

public abstract class ContaBancaria
{
    private long    cpfCliente;
    private int     numBanco;
    private int     numAgencia;
    private int     numConta;
    private double  saldo;
    private final   List<EventoConta> movimentacao;
    private boolean ativa;
    
    public ContaBancaria()
    {
        movimentacao = new ArrayList<>();
        setAtiva(true);
    }
    
    public ContaBancaria(String cpf, int numBanco, int numAgencia, int numConta) throws Exception
    {
        setCpfCliente(cpf);
        setNumBanco(numBanco);
        setNumAgencia(numAgencia);
        setNumConta(numConta);
        movimentacao = new ArrayList<>();
        setAtiva(true);
    }
    
    public void sacar(double valor) throws Exception
    {
        if (!isAtiva())
            throw new Exception("Não é possível movimentar contas inativas");

        if (saldo < valor)
            throw new Exception("Saldo insuficiente");

        movimentacao.add(new EventoConta(movimentacao.size() + 1, "Saque", TipoOper.diminuiSaldo, LocalDateTime.now(), getSaldo(), valor));
        saldo -= valor;
    }
    
    public void depositar(double valor) throws Exception
    {
        if (!isAtiva())
            throw new Exception("Não é possível movimentar contas inativas");
        
        movimentacao.add(new EventoConta(movimentacao.size() + 1, "Depósito", TipoOper.aumentaSaldo, LocalDateTime.now(), getSaldo(), valor));
        saldo += valor;
    }
    
    public String imprimeExtrato()
    {
        ListIterator<EventoConta> iterator = movimentacao.listIterator();
        String extrato;

        extrato = "Agencia: " + getNumAgencia() + "\n"   +
                  "Conta:   " + getNumConta()   + "\n\n" +
                  "Movimentação: \n";

        if (!iterator.hasNext())
            extrato += "Nenhuma transação disponível";

        while (iterator.hasNext())
        {
            EventoConta evento = iterator.next();
            
            extrato += evento.toString();
            if(iterator.hasNext())
                extrato += "\n---------------------------------\n";
        }
        
        return extrato;
    }

    public String getCpfCliente()
    {
        return Cpf.aplicaMascara(Long.toString(cpfCliente));
    }

    public void setCpfCliente(String cpfCliente) throws Exception
    {
        if (!Cpf.isCpf(cpfCliente))
            throw new Exception(cpfCliente + "não é um CPF válido");
        
        this.cpfCliente = Long.parseLong(Cpf.removeMascara(cpfCliente));
    }

    public int getNumBanco()
    {
        return numBanco;
    }

    public void setNumBanco(int numBanco)
    {
        this.numBanco = numBanco;
    }

    public int getNumAgencia()
    {
        return numAgencia;
    }

    public void setNumAgencia(int numAgencia)
    {
        this.numAgencia = numAgencia;
    }

    public int getNumConta()
    {
        return numConta;
    }

    public void setNumConta(int numConta)
    {
        this.numConta = numConta;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public boolean isAtiva()
    {
        return ativa;
    }

    public void setAtiva(boolean ativa)
    {
        this.ativa = ativa;
    }
}