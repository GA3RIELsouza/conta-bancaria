package org.br.controlecontas;

import java.sql.Connection;

import connection.MySQL;

public class App {
    public static void main(String[] args) throws Exception {
    	Connection con = MySQL.conectar();

        if(con != null)
            System.out.println("Sucesso!");
    }
}