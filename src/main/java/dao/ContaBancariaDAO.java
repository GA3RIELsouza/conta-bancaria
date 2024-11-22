package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import connection.MySQL;
import interfaces.IContaBancariaDAO;
import models.ContaBancaria;

public class ContaBancariaDAO implements IContaBancariaDAO {
    public void incluir(ContaBancaria contaBancaria) {
		incluir(contaBancaria, false);
	}
	
	public long incluir(ContaBancaria contaBancaria, boolean retornaId) {
		if (contaBancaria == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO conta_bancaria (codigo_banco, num_agencia, saldo, data_abertura, id_titular)
                                VALUES (?, ?, ?, ?, ?)
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		Long id;
		
		try {
			con = MySQL.conectar();
			
			if (retornaId)
				pstm = con.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			else
				pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, contaBancaria.getCodigoBanco());
			pstm.setInt(2, contaBancaria.getNumAgencia());
			pstm.setDouble(3, contaBancaria.getSaldo());
			pstm.setDate(4, contaBancaria.getDataAbertura());
			pstm.setLong(5, contaBancaria.getIdTitular());
			
			pstm.execute();
			
			if (retornaId) {
				ResultSet rs = pstm.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
			}
			else
				id = (long) -1;
						
			pstm.close();

			return id;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir conta bancaria:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(ContaBancaria contaBancaria) {
		if (contaBancaria == null)
			return;
		
		final String comando =	"""
                                UPDATE conta_bancaria
			                    SET codigo_banco = ?, num_agencia = ?, saldo = ?, data_abertura = ?, id_titular = ?
		                        WHERE codigo = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, contaBancaria.getCodigoBanco());
			pstm.setInt(2, contaBancaria.getNumAgencia());
			pstm.setDouble(3, contaBancaria.getSaldo());
			pstm.setDate(4, contaBancaria.getDataAbertura());
			pstm.setLong(5, contaBancaria.getIdTitular());
			pstm.setLong(6, contaBancaria.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar conta bancaria:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =	"""
                                DELETE FROM conta_bancaria
		                        WHERE id = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);

			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao excluir conta_bancaria:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public ContaBancaria consultarPorId(ContaBancaria contaBancaria) {
		final String comando =	"""
                                SELECT codigo_banco, num_agencia, saldo, data_abertura, id_titular
				                FROM conta_bancaria
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, contaBancaria.getId());
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				contaBancaria.setCodigoBanco(rs.getLong("codigo_banco"));
				contaBancaria.setNumAgencia(rs.getInt("num_agencia"));
				contaBancaria.depositar(rs.getDouble("saldo"));
				contaBancaria.setDataAbertura(rs.getDate("data_abertura"));
				contaBancaria.setIdTitular(rs.getLong("id_titular"));

				pstm.close();
				MySQL.desconectar(con);
				
				return contaBancaria;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta bancaria:\n" + e.getMessage());
		}
	}

	public List<ContaBancaria> consultarTodosPorTitular(List<ContaBancaria> contasBancarias) {
		return null;
	}

    public List<ContaBancaria> consultarTodosPorBanco(List<ContaBancaria> contasBancarias) {
		return null;
	}

	public List<ContaBancaria> consultarTodos() {
		return null;
	}
}
