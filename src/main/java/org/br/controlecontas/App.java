package org.br.controlecontas;

import java.sql.Connection;
import java.sql.Date;
import connection.MySQL;
import dao.*;
import enums.*;
import models.*;

public class App {
    public static void main(String[] args) throws Exception {
        Date dataAtual = new java.sql.Date(new java.util.Date().getTime());

        // MySQL
        Connection conexaoTeste = MySQL.conectar();
        MySQL.desconectar(conexaoTeste);

        System.out.println("MySQL ................: OK!");

        // LocalidadeDAO
        LocalidadeDAO localidadeDAO = new LocalidadeDAO();
        long idLocalidade;
        long cepOriginal = 89046420;

        idLocalidade = localidadeDAO.incluir(new Localidade(-1, cepOriginal), true);
        localidadeDAO.consultarPorId(idLocalidade);
        localidadeDAO.consultarPorCep(cepOriginal);
        localidadeDAO.consultarPorUf(Estado.SC);
        localidadeDAO.consultarTodos();
        localidadeDAO.atualizar(new Localidade(idLocalidade, 89012001));
        localidadeDAO.excluir(idLocalidade);
        
        System.out.println("LocalidadeDAO ........: OK!");

        idLocalidade = localidadeDAO.incluir(new Localidade(-1, cepOriginal), true);

        // PessoaFisicaDAO
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        long idPessoaFisica;

        idPessoaFisica = pessoaFisicaDAO.incluir(new PessoaFisica(-1, idLocalidade, 1,
            "Casa fundos", Situacao.Ativo, "132.428.729-24",
            "Gabriel Leandro de Souza", new java.sql.Date(System.currentTimeMillis()), Sexo.Masculino), true);
        pessoaFisicaDAO.consultarPorId(idPessoaFisica);
        pessoaFisicaDAO.consultarPorSituacao(Situacao.Ativo);
        pessoaFisicaDAO.consultarTodos();
        pessoaFisicaDAO.atualizar(new PessoaFisica(idPessoaFisica, idLocalidade, 1,
            "Casa fundos", Situacao.Ativo, "457.703.109-44",
            "Romeu da Silva", new java.sql.Date(System.currentTimeMillis()), Sexo.Masculino));
        pessoaFisicaDAO.excluir(idPessoaFisica);
        
        System.out.println("PessoaFisicaDAO ......: OK!");

        idPessoaFisica = pessoaFisicaDAO.incluir(new PessoaFisica(-1, idLocalidade, 253,
            "Casa fundos", Situacao.Ativo, "132.428.729-24",
            "Gabriel Leandro de Souza", new java.sql.Date(System.currentTimeMillis()), Sexo.Masculino), true);

        // PessoaJuridicaDAO
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        long idPessoaJuridica;
        String cnpjPessoaJuridica = "99.136.369/0001-93";

        idPessoaJuridica = pessoaJuridicaDAO.incluir(new PessoaJuridica(-1, idLocalidade, 253,
            "Casa fundos", Situacao.Ativo, cnpjPessoaJuridica,
            "Davi Brito", "Davi Brito Empresa", 123), true);
        pessoaJuridicaDAO.consultarPorId(idPessoaJuridica);
        pessoaJuridicaDAO.consultarPorSituacao(Situacao.Ativo);
        pessoaJuridicaDAO.consultarTodos();
        pessoaJuridicaDAO.atualizar(new PessoaJuridica(idPessoaJuridica, idLocalidade, 253,
            "Casa fundos", Situacao.Ativo, "47.818.201/0001-64",
            "Davyd Britts", "David Britts Empresa", 321));
        pessoaJuridicaDAO.excluir(idPessoaJuridica);
        
        System.out.println("PessoaJuridicaDAO ....: OK!");

        idPessoaJuridica = pessoaJuridicaDAO.incluir(new PessoaJuridica(-1, idLocalidade, 253,
            "Casa fundos", Situacao.Ativo, cnpjPessoaJuridica,
            "Davi Brito", "Davi Brito Empresa", 123), true);
        
        // TelefoneDAO
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        long idTelefone;

        idTelefone = telefoneDAO.incluir(new Telefone(-1, idPessoaFisica, "(47) 99291-0530", TipoTel.Movel), true);
        telefoneDAO.consultarPorId(idTelefone);
        telefoneDAO.consultarTodos();
        telefoneDAO.consultarPorPessoa(idPessoaFisica);
        telefoneDAO.atualizar(new Telefone(idTelefone, idPessoaFisica, "(31) 2570-6173", TipoTel.Fixo));
        telefoneDAO.excluir(idTelefone);

        System.out.println("TelefoneDAO ..........: OK!");

        // BancoDAO
        BancoDAO bancoDAO = new BancoDAO();
        long codigoBanco = 654123;
        
        bancoDAO.incluir(new Banco(codigoBanco, "Banco do Java", "999.99", "9.999-99"));
        bancoDAO.consultarPorId(codigoBanco);
        bancoDAO.consultarTodos();
        bancoDAO.atualizar(new Banco(codigoBanco, "Jabank", "99.999", "99.999-9"));
        telefoneDAO.excluir(codigoBanco);

        System.out.println("BancoDAO .............: OK!");

        // ContaCorrenteDAO
        ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO();
        long idContaCorrente;

        idContaCorrente = contaCorrenteDAO.incluir(new ContaCorrente(-1, codigoBanco, 1, 1, dataAtual,
            idPessoaFisica, 1500, 500), true);
        contaCorrenteDAO.consultarPorId(idContaCorrente);
        contaCorrenteDAO.consultarPorTitular(idPessoaFisica);
        contaCorrenteDAO.consultarTodos();
        contaCorrenteDAO.atualizar(new ContaCorrente(-1, codigoBanco, 1, 1, dataAtual,
            idPessoaFisica, 2500, 1500));
        contaCorrenteDAO.excluir(idContaCorrente);

        System.out.println("ContaCorrenteDAO .....: OK!");

        idContaCorrente = contaCorrenteDAO.incluir(new ContaCorrente(-1, codigoBanco, 1, 1, dataAtual,
            idPessoaFisica, 1500, 500), true);

        // EventoDAO
        EventoDAO eventoDAO = new EventoDAO();
        long idEvento;

        idEvento = eventoDAO.incluir(new Evento(-1, "Depósito",
            TipoMov.aumentaSaldo, Situacao.Ativo), true);
        eventoDAO.consultarPorId(idEvento);
        eventoDAO.consultarPorSituacao(Situacao.Ativo);
        eventoDAO.consultarTodos();
        eventoDAO.atualizar(new Evento(idEvento, "Nada",
            TipoMov.naoAlteraSaldo, Situacao.Inativo));
        eventoDAO.excluir(idEvento);

        System.out.println("EventoDAO ............: OK!");

        idEvento = eventoDAO.incluir(new Evento(-1, "Depósito",
            TipoMov.aumentaSaldo, Situacao.Ativo), true);

        // MovimentacaoDAO
        MovimentacaoDAO movimentacaoDAO = new MovimentacaoDAO();
        long idMovimentacao;

        idMovimentacao = movimentacaoDAO.incluir(new Movimentacao(-1, idContaCorrente, dataAtual,
            idEvento, 1500), true);
        movimentacaoDAO.consultarPorId(idMovimentacao);
        movimentacaoDAO.consultarTodos();
        movimentacaoDAO.atualizar(new Movimentacao(-1, idContaCorrente, dataAtual, idEvento, 2500));
        movimentacaoDAO.excluir(idMovimentacao);

        System.out.println("MovimentacaoDAO ......: OK!");

        // ContaEspecialDAO
        ContaEspecialDAO contaEspecialDAO = new ContaEspecialDAO();
        long idContaEspecial;

        contaEspecialDAO.promoverConta(idContaCorrente, 2000, dataAtual);
        contaEspecialDAO.rebaixarConta(idContaCorrente);
        idContaEspecial = contaEspecialDAO.incluir(new ContaEspecial(-1, codigoBanco, 2, 2, dataAtual,
            idPessoaFisica, 1500, 500, 5000, dataAtual), true);
        contaEspecialDAO.consultarPorId(idContaEspecial);
        contaEspecialDAO.consultarPorTitular(idPessoaFisica);
        contaEspecialDAO.consultarTodos();
        contaEspecialDAO.atualizar(new ContaEspecial(-1, codigoBanco, 2, 2, dataAtual,
            idPessoaFisica, 2500, 1500, 10000, dataAtual));
        contaEspecialDAO.excluir(idContaEspecial);

        System.out.println("ContaEspecialDAO .....: OK!");

        // ContaEspecialDAO
        ContaSalarioDAO contaSalarioDAO = new ContaSalarioDAO();
        long idContaSalario;

        idContaSalario = contaSalarioDAO.incluir(new ContaSalario(-1, codigoBanco, 2, 2, dataAtual,
            idPessoaJuridica, cnpjPessoaJuridica, 1000, 1000, true, idContaCorrente), true);
        contaSalarioDAO.consultarPorId(idContaSalario);
        contaSalarioDAO.consultarPorTitular(idPessoaJuridica);
        contaSalarioDAO.consultarPorCnpj(cnpjPessoaJuridica);
        contaSalarioDAO.consultarTodos();
        contaSalarioDAO.atualizar(new ContaSalario(idContaSalario, codigoBanco, 2, 2, dataAtual,
            idPessoaJuridica, cnpjPessoaJuridica, 2000, 2000, true, idContaCorrente));
        contaSalarioDAO.excluir(idContaSalario);

        System.out.println("ContaSalarioDAO ......: OK!");

        // IndiceRemuneracaoDAO
        IndiceRemuneracaoDAO indiceRemuneracaoDAO = new IndiceRemuneracaoDAO();
        long idIndiceRemuneracao;

        idIndiceRemuneracao = indiceRemuneracaoDAO.incluir(new IndiceRemuneracao(-1, "Indice de remnuneração", 15, Situacao.Ativo), true);
        indiceRemuneracaoDAO.consultarPorId(idIndiceRemuneracao);
        indiceRemuneracaoDAO.consultarTodos();
        indiceRemuneracaoDAO.atualizar(new IndiceRemuneracao(idIndiceRemuneracao, "Indice de remnuneração", 0, Situacao.Inativo));
        indiceRemuneracaoDAO.excluir(idIndiceRemuneracao);

        System.out.println("IndiceRemuneracaoDAO .: OK!");

        idIndiceRemuneracao = indiceRemuneracaoDAO.incluir(new IndiceRemuneracao(-1, "Indice de remnuneração", 15, Situacao.Ativo), true);

        // CotacaoDAO
        CotacaoDAO cotacaoDAO = new CotacaoDAO();
        long idCotacao;

        idCotacao = cotacaoDAO.incluir(new Cotacao(-1, idIndiceRemuneracao, dataAtual, 500), true);
        cotacaoDAO.consultarPorId(idCotacao);
        cotacaoDAO.consultarTodos();
        cotacaoDAO.atualizar(new Cotacao(-1, idIndiceRemuneracao, dataAtual, 250));
        cotacaoDAO.excluir(idCotacao);

        System.out.println("CotacaoDAO ...........: OK!");

        // ContaPoupancaDAO
        ContaPoupancaDAO contaPoupancaDAO = new ContaPoupancaDAO();
        long idContaPoupanca;

        idContaPoupanca = contaPoupancaDAO.incluir(new ContaPoupanca(-1, codigoBanco, 3, 3, dataAtual,
            idPessoaJuridica, idIndiceRemuneracao, 50), true);
        contaPoupancaDAO.consultarPorId(idContaPoupanca);
        contaPoupancaDAO.consultarPorTitular(idPessoaJuridica);
        contaPoupancaDAO.consultarTodos();
        contaPoupancaDAO.atualizar(new ContaPoupanca(idContaPoupanca, codigoBanco, 3, 3, dataAtual,
            idPessoaJuridica, idIndiceRemuneracao, 75));
        contaPoupancaDAO.excluir(idContaPoupanca);

        System.out.println("ContaPoupancaDAO .....: OK!");

        contaCorrenteDAO.excluir(idContaCorrente);
        indiceRemuneracaoDAO.excluir(idIndiceRemuneracao);
        pessoaFisicaDAO.excluir(idPessoaFisica);
        pessoaJuridicaDAO.excluir(idPessoaJuridica);
        localidadeDAO.excluir(idLocalidade);
        eventoDAO.excluir(idEvento);
        bancoDAO.excluir(codigoBanco);
    }
}
