package org.br.controlecontas;

import models.*;

import java.sql.Connection;

import connection.MySQL;
import dao.*;
import enums.Estado;
import enums.Sexo;
import enums.Situacao;
import enums.TipoTel;

public class App {
    public static void main(String[] args) throws Exception {
        // Testando conexão ao banco MySQL
        Connection conexaoTeste = MySQL.conectar();
        MySQL.desconectar(conexaoTeste);

        System.out.println("Classe MySQL = OK!");

        // Testando métodos do DAO da localidade
        LocalidadeDAO localidadeDAO = new LocalidadeDAO();
        long idLocalidade;
        long cepOriginal = 89046420;
        long cepAtualizado = 89012001;

        idLocalidade = localidadeDAO.incluir(new Localidade(-1, cepOriginal), true);
        localidadeDAO.consultarPorId(idLocalidade);
        localidadeDAO.consultarPorCep(cepOriginal);
        localidadeDAO.consultarTodos();
        localidadeDAO.consultarTodosPorUf(Estado.SC);
        localidadeDAO.atualizar(new Localidade(idLocalidade, cepAtualizado));
        localidadeDAO.excluir(idLocalidade);
        
        System.out.println("Classe LocalidadeDAO = OK!");

        idLocalidade = localidadeDAO.incluir(new Localidade(-1, cepOriginal), true);

        // Testando métodos do DAO da pessoa física e da classe pessoa por consequência
        PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO();
        long idPessoaFisica;

        idPessoaFisica = pessoaFisicaDAO.incluir(new PessoaFisica(-1, idLocalidade, 1, "Casa fundos", Situacao.Ativo,
            "132.428.729-24", "Gabriel Leandro de Souza", new java.sql.Date(System.currentTimeMillis()), Sexo.Masculino), true);
        pessoaFisicaDAO.consultarPorId(idPessoaFisica);
        pessoaFisicaDAO.consultarTodos();
        pessoaFisicaDAO.atualizar(new PessoaFisica(idPessoaFisica, idLocalidade, 1, "Casa fundos", Situacao.Ativo,
            "457.703.109-44", "Romeu da Silva", new java.sql.Date(System.currentTimeMillis()), Sexo.Masculino));
        pessoaFisicaDAO.excluir(idPessoaFisica);
        
        System.out.println("Classe PessoaFisicaDAO = OK!");

        // Testando métodos do DAO da pessoa juridica e da classe pessoa por consequência
        PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO();
        long idPessoaJuridica;

        idPessoaJuridica = pessoaJuridicaDAO.incluir(new PessoaJuridica(-1, idLocalidade, 253, "Casa fundos", Situacao.Ativo,
            "99.136.369/0001-93", "Davi Brito", "Davi Brito Empresa", "INSCRIÇÃO1"), true);
        pessoaJuridicaDAO.consultarPorId(idPessoaJuridica);
        pessoaJuridicaDAO.consultarTodos();
        pessoaJuridicaDAO.atualizar(new PessoaJuridica(idPessoaJuridica, idLocalidade, 253, "Casa fundos", Situacao.Ativo,
            "47.818.201/0001-64", "Davyd Britts", "David Britts Empresa", "INSCRIÇÃO2"));
        pessoaJuridicaDAO.excluir(idPessoaJuridica);
        
        System.out.println("Classe PessoaJuridicaDAO = OK!");

        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.consultarTodos();

        System.out.println("Classe PessoaDAO = OK!");

        idPessoaFisica = pessoaFisicaDAO.incluir(new PessoaFisica(-1, idLocalidade, 253, "Casa fundos", Situacao.Ativo,
            "132.428.729-24", "Gabriel Leandro de Souza", new java.sql.Date(System.currentTimeMillis()), Sexo.Masculino), true);
        
        // Testando métodos do DAO do telefone
        TelefoneDAO telefoneDAO = new TelefoneDAO();
        long[] idTelefone;

        idTelefone = telefoneDAO.incluir(new Telefone(idPessoaFisica, -1, "(47) 99291-0530", TipoTel.Movel), true);
        telefoneDAO.consultarPorId(idTelefone[0], idTelefone[1]);
        telefoneDAO.consultarTodos();
        telefoneDAO.consultarTodosPorPessoa(idTelefone[0]);
        telefoneDAO.atualizar(new Telefone(idTelefone[0], idTelefone[1], "(31) 2570-6173", TipoTel.Fixo));
        telefoneDAO.excluir(idTelefone[0], idTelefone[1]);

        System.out.println("Classe TelefoneDAO = OK!");
    }
}
