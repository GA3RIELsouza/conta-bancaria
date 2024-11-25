package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.IMovimentacaoDAO;
import models.Movimentacao;

public class MovimentacaoDAO implements IMovimentacaoDAO {
    public void incluir(Movimentacao movimentacao) {
		incluir(movimentacao, false);
	}
	
	public long incluir(Movimentacao movimentacao, boolean retornaCodigo) {
		if (movimentacao == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO movimentacao (id_conta_bancaria, data_movimentacao, id_evento, valor)
                                VALUES (?, ?, ?, ?)
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		Long codigo;
		
		try {
			con = MySQL.conectar();
			
			if (retornaCodigo)
				pstm = con.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			else
				pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, movimentacao.getIdContaBancaria());
            pstm.setDate(2, movimentacao.getDataMovimentacao());
			pstm.setLong(3, movimentacao.getIdEvento());
			pstm.setDouble(4, movimentacao.getValor());
			
			pstm.execute();
			
			if (retornaCodigo) {
				ResultSet rs = pstm.getGeneratedKeys();
				rs.next();
				codigo = rs.getLong(1);
			}
			else
				codigo = (long) -1;
						
			pstm.close();

			return codigo;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir movimentacao:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(Movimentacao movimentacao) {
		if (movimentacao == null)
			return;
		
		final String comando =	"""
                                UPDATE movimentacao
			                    SET id_conta_bancaria = ?, data_movimentacao = ?, id_evento = ?, valor = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, movimentacao.getIdContaBancaria());
			pstm.setDate(2, movimentacao.getDataMovimentacao());
			pstm.setLong(3, movimentacao.getIdEvento());
			pstm.setDouble(4, movimentacao.getValor());
			pstm.setLong(5, movimentacao.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar movimentacao:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =	"""
                                DELETE FROM movimentacao
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
			throw new RuntimeException("Problemas ao excluir movimentacao:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public Movimentacao consultarPorId(long id) {
		final String comando =	"""
                                SELECT id_conta_bancaria, data_movimentacao, id_evento, valor
				                FROM movimentacao
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Movimentacao            movimentacao;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				movimentacao = new Movimentacao();
				
				movimentacao.setId(id);
				movimentacao.setIdContaBancaria(rs.getLong("id_conta_bancaria"));
				movimentacao.setDataMovimentacao(rs.getDate("data_movimentacao"));
				movimentacao.setIdEvento(rs.getInt("id_evento"));
				movimentacao.setValor(rs.getDouble("valor"));

				pstm.close();
				MySQL.desconectar(con);
				
				return movimentacao;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar movimentacao:\n" + e.getMessage());
		}
	}

	public List<Movimentacao> consultarTodos() {
		final String comando =	"""
                                SELECT id, id_conta_bancaria, data_movimentacao, id_evento, valor
	                            FROM movimentacao
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Movimentacao            movimentacao;
		List<Movimentacao>      listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				movimentacao = new Movimentacao();
                listaRetorno = new ArrayList<>();
				
				movimentacao.setId(rs.getLong("id"));
				movimentacao.setIdContaBancaria(rs.getLong("id_conta_bancaria"));
				movimentacao.setDataMovimentacao(rs.getDate("data_movimentacao"));
				movimentacao.setIdEvento(rs.getInt("id_evento"));
				movimentacao.setValor(rs.getDouble("valor"));

				listaRetorno.add(movimentacao);				
			}

			pstm.close();
			MySQL.desconectar(con);

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar movimentacao:\n" + e.getMessage());
		}
	}
}
