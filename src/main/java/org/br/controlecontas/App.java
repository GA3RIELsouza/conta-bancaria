package org.br.controlecontas;

import models.*;

import java.sql.Connection;

import connection.MySQL;
import dao.*;
import enums.Estado;

public class App {
    public static void main(String[] args) throws Exception {
        // Testando conexão ao banco MySQL
        Connection conexaoTeste = MySQL.conectar();
        MySQL.desconectar(conexaoTeste);

        System.out.println("Classe MySQL = OK!");

        // Testando métodos do DAO da localidade
        LocalidadeDAO localidadeDAO = new LocalidadeDAO();
        long chaveRegistro;
        long cepOriginal = 89046420;
        long cepAtualizado = 89012001;

        chaveRegistro = localidadeDAO.incluir(new Localidade(-1, cepOriginal), true);
        System.out.println(localidadeDAO.consultarPorId(chaveRegistro).toString());
        System.out.println(localidadeDAO.consultarPorCep(cepOriginal).toString());
        System.out.println(localidadeDAO.consultarTodos().toString());
        System.out.println(localidadeDAO.consultarTodosPorUf(Estado.SC).toString());
        localidadeDAO.atualizar(new Localidade(chaveRegistro, cepAtualizado));
        localidadeDAO.excluir(chaveRegistro);
        
        System.out.println("Classe LocalidadeDAO = OK!");
    }
}
