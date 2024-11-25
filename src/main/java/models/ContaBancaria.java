package models;

import java.sql.Date;

public abstract class ContaBancaria {
    private long    id;
    private long    codigoBanco;
    private long    numAgencia;
    private long    numConta;
    private double  saldo;
    private Date    dataAbertura;
    private long    idTitular;

    public ContaBancaria(){}
    
    public ContaBancaria(long id, long codigoBanco, long numAgencia, long numConta, Date dataAbertura, long idTitular) {
        setId(id);
        setCodigoBanco(codigoBanco);
        setNumAgencia(numAgencia);
        setNumConta(numConta);
        this.saldo = 0;
        setDataAbertura(dataAbertura);
        setIdTitular(idTitular);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCodigoBanco() {
        return this.codigoBanco;
    }

    public void setCodigoBanco(long idBanco) {
        this.codigoBanco = idBanco;
    }

    public long getNumAgencia() {
        return this.numAgencia;
    }

    public void setNumAgencia(long numAgencia) {
        this.numAgencia = numAgencia;
    }

    public void sacar(double valor) {
        if (valor < 0)
            throw new RuntimeException("Valor de saque inválido: R$" + valor);
        
        if (valor > this.saldo)
            throw new RuntimeException("Valor de saque inválido: R$" + valor + ", saldo em conta: R$" + this.saldo);

        this.saldo -= valor;
    }

    public void depositar(double valor) {
        if(valor < 0)
            throw new RuntimeException("Valor de depósito inválido: R$" + valor);
        
        this.saldo += valor;
    }

    public long getNumConta() {
        return this.numConta;
    }

    public void setNumConta(long numConta) {
        this.numConta = numConta;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public Date getDataAbertura() {
        return this.dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public long getIdTitular() {
        return this.idTitular;
    }

    public void setIdTitular(long idTitular) {
        this.idTitular = idTitular;
    }
}
