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
	
	public long[] incluir(Telefone telefone, boolean retornaId) {
		if (telefone == null)
			return null;
		
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

			return new long[]{telefone.getIdPessoa(), id};
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
			                    SET numero = ?, tipo = ?
		                        WHERE id_pessoa = ?, id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, telefone.getNumero());
			pstm.setInt(2, telefone.getTipo().getId());
			pstm.setLong(3, telefone.getIdPessoa());
			pstm.setLong(4, telefone.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar telefone:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long idPessoa, long id) {
		final String comando =	"""
                                DELETE FROM telefone
		                        WHERE id_pessoa = ?, id = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, idPessoa);
			pstm.setLong(2, id);

			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao excluir telefone:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public Telefone consultarPorId(long idPessoa, long id) {
		final String comando =	"""
                                SELECT id_pessoa, numero, tipo
				                FROM telefone
                			    WHERE id_pessoa = ?, id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Telefone          telefone;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			pstm.setLong(2, idPessoa);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				telefone = new Telefone();
				
				telefone.setIdPessoa(rs.getLong("id_pessoa"));
				telefone.setId(id);
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
                                SELECT id_pessoa, id, numero, tipo
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
				
				telefone.setIdPessoa(rs.getLong("id_pessoa"));
				telefone.setId(rs.getLong("id"));
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
				
				telefone.setIdPessoa(idPessoa);
				telefone.setId(rs.getLong("id"));
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
