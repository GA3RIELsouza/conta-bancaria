package org.br.controlecontas;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

import classes.ContaPoupanca;
import classes.Pessoa;

public class App
{
    public static void main(String[] args) throws Exception
    {
        List<Pessoa> pessoas = new ArrayList<>();
        List<ContaPoupanca> contasPoupanca = new ArrayList<>();

        pessoas.add(new Pessoa("635.198.050-06", "João Batista",    "(47) 91324-1423"));
        pessoas.add(new Pessoa("080.169.590-24", "Maria Aparecida", "(88) 94813-1390"));

        contasPoupanca.add(new ContaPoupanca(pessoas.get(0).getCpf(), 001, 1234, 567890));
        contasPoupanca.add(new ContaPoupanca(pessoas.get(1).getCpf(), 033, 9876, 54321));

        contasPoupanca.get(0).depositar(10000.00);
        contasPoupanca.get(0).sacar(5000.00);
        contasPoupanca.get(0).sacar(2500.00);
        contasPoupanca.get(0).depositar(7500.00);
        contasPoupanca.get(1).depositar(1000.00);

        ListIterator<Pessoa> pessoasIterator = pessoas.listIterator();
        ListIterator<ContaPoupanca> contasPoupancIterator = contasPoupanca.listIterator();
        
        System.out.println("=====PESSOAS===================================");

        while(pessoasIterator.hasNext())
        {
            Pessoa pessoa = pessoasIterator.next();
            System.out.println(pessoa.toString());
            if(pessoasIterator.hasNext())
                System.out.println("~~~~~~~~~~~~~~");
        }

        System.out.println("===============================================\n");

        System.out.println("=====CONTAS=POUPANÇA===========================");

        while(contasPoupancIterator.hasNext())
        {
            ContaPoupanca contaPoupanca = contasPoupancIterator.next();
            System.out.println(contaPoupanca.imprimeExtrato());
            if(contasPoupancIterator.hasNext())
                System.out.println("~~~~~~~~~~~~~~");
        }

        System.out.println("===============================================");
    }
}