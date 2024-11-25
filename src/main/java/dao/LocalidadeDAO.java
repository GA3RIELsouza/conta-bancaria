package dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Localidade;
import connection.MySQL;
import enums.Estado;
import interfaces.ILocalidadeDAO;

public final class LocalidadeDAO implements ILocalidadeDAO {
	public void incluir(Localidade localidade) {
		incluir(localidade, false);
	}
	
	public long incluir(Localidade localidade, boolean retornaId) {
		if (localidade == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO localidade (cep, estado, cidade, bairro, logradouro)
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
			
			pstm.setLong(1, localidade.getCep());
			pstm.setString(2, localidade.getEstado().getNome());
			pstm.setString(3, localidade.getCidade());
			pstm.setString(4, localidade.getBairro());
			pstm.setString(5, localidade.getLogradouro());
			
			pstm.execute();
			
			if (retornaId) {
				ResultSet rs = pstm.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
			}
			else
				id = (long) -1;

			return id;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void atualizar(Localidade localidade) {
		if (localidade == null)
			return;
		
		final String comando =	"""
                                UPDATE localidade
			                    SET cep = ?, estado = ?, cidade = ?, bairro = ?, logradouro = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, localidade.getCep());
			pstm.setString(2, localidade.getEstado().getNome());
			pstm.setString(3, localidade.getCidade());
			pstm.setString(4, localidade.getBairro());
			pstm.setString(5, localidade.getLogradouro());
			pstm.setLong(6, localidade.getId());
			
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void excluir(long id) {
		final String comando =	"""
                                DELETE FROM localidade
		                        WHERE id = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);

			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao excluir localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
	
	public Localidade consultarPorId(long id) {
		final String comando =	"""
                                SELECT cep, estado, cidade, bairro, logradouro
				                FROM localidade
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Localidade        localidade;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				localidade = new Localidade();
				
				localidade.setId(id);
				localidade.setCep(rs.getInt("cep"));
				localidade.setEstado(Estado.fromNome(rs.getString("estado")));
				localidade.setCidade(rs.getString("cidade"));
				localidade.setBairro(rs.getString("bairro"));
				localidade.setLogradouro(rs.getString("logradouro"));
				
				return localidade;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public Localidade consultarPorCep(long cep) {
		final String comando =	"""
                                SELECT id, estado, cidade, bairro, logradouro
				                FROM localidade
                			    WHERE cep = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Localidade        localidade;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, cep);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				localidade = new Localidade();
				
				localidade.setId(rs.getLong("id"));
				localidade.setCep(cep);
				localidade.setEstado(Estado.fromNome(rs.getString("estado")));
				localidade.setCidade(rs.getString("cidade"));
				localidade.setBairro(rs.getString("bairro"));
				localidade.setLogradouro(rs.getString("logradouro"));

				return localidade;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public List<Localidade> consultarPorUf(Estado uf) {
		final String comando =	"""
                                SELECT id, cep, estado, cidade, bairro, logradouro
	                            FROM localidade
			                    WHERE estado = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Localidade        localidade;
		List<Localidade>  listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setString(1, uf.getNome());
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				localidade = new Localidade();
                listaRetorno = new ArrayList<>();
				
				localidade.setId(rs.getLong("id"));
				localidade.setCep(rs.getInt("cep"));
				localidade.setEstado(Estado.fromNome(rs.getString("estado")));
				localidade.setCidade(rs.getString("cidade"));
				localidade.setBairro(rs.getString("bairro"));
				localidade.setLogradouro(rs.getString("logradouro"));

				listaRetorno.add(localidade);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }	
	}

	public List<Localidade> consultarTodos() {
		final String comando =	"""
                                SELECT id, cep, estado, cidade, bairro, logradouro
	                            FROM localidade
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Localidade        localidade;
		List<Localidade>  listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				localidade = new Localidade();
                listaRetorno = new ArrayList<>();
				
				localidade.setId(rs.getLong("id"));
				localidade.setCep(rs.getInt("cep"));
				localidade.setEstado(Estado.fromNome(rs.getString("estado")));
				localidade.setCidade(rs.getString("cidade"));
				localidade.setBairro(rs.getString("bairro"));
				localidade.setLogradouro(rs.getString("logradouro"));

				listaRetorno.add(localidade);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar localidade:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null)
					MySQL.desconectar(con);
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
}
