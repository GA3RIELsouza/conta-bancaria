package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import enums.TipoTel;
import interfaces.ITelefoneDAO;
import models.Telefone;

public class TelefoneDAO implements ITelefoneDAO {
    public void incluir(Telefone telefone) {
		incluir(telefone, false);
	}
	
	public long incluir(Telefone telefone, boolean retornaId) {
		if (telefone == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO telefone (id_pessoa, numero, tipo)
                                VALUES (?, ?, ?)
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
			
			pstm.setLong(1, telefone.getIdPessoa());
			pstm.setString(2, telefone.getNumero());
			pstm.setInt(3, telefone.getTipo().getId());
			
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
			throw new RuntimeException("Problemas ao incluir telefone:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(Telefone telefone) {
		if (telefone == null)
			return;
		
		final String comando =	"""
                                UPDATE telefone
			                    SET id_pessoa = ?, numero = ?, tipo = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, telefone.getIdPessoa());
			pstm.setString(2, telefone.getNumero());
			pstm.setInt(3, telefone.getTipo().getId());
			pstm.setLong(4, telefone.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar telefone:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =	"""
                                DELETE FROM telefone
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
			throw new RuntimeException("Problemas ao excluir telefone:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public Telefone consultarPorId(long id) {
		final String comando =	"""
                                SELECT id_pessoa, numero, tipo
				                FROM telefone
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Telefone          telefone;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				telefone = new Telefone();
				
				telefone.setId(id);
				telefone.setIdPessoa(rs.getLong("id_pessoa"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setTipo(TipoTel.fromId(rs.getInt("tipo")));

				pstm.close();
				MySQL.desconectar(con);
				
				return telefone;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar telefone:\n" + e.getMessage());
		}
	}

	public List<Telefone> consultarTodos() {
		final String comando =	"""
                                SELECT id, id_pessoa, numero, tipo
	                            FROM telefone
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Telefone          telefone;
		List<Telefone>    listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				telefone = new Telefone();
                listaRetorno = new ArrayList<>();
				
				telefone.setId(rs.getLong("id"));
				telefone.setIdPessoa(rs.getLong("id_pessoa"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setTipo(TipoTel.fromId(rs.getInt("tipo")));

				listaRetorno.add(telefone);				
			}

			pstm.close();
			MySQL.desconectar(con);

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar telefone:\n" + e.getMessage());
		}		
	}

	public List<Telefone> consultarTodosPorPessoa(long idPessoa) {
		final String comando =	"""
                                SELECT id, numero, tipo
	                            FROM telefone
			                    WHERE id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Telefone          telefone;
		List<Telefone>    listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, idPessoa);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				telefone = new Telefone();
                listaRetorno = new ArrayList<>();
				
				telefone.setId(rs.getLong("id"));
				telefone.setIdPessoa(idPessoa);
				telefone.setNumero(rs.getString("numero"));
				telefone.setTipo(TipoTel.fromId(rs.getInt("tipo")));

				listaRetorno.add(telefone);				
			}

			pstm.close();
			MySQL.desconectar(con);

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar telefone:\n" + e.getMessage());
		}		
	}
}
