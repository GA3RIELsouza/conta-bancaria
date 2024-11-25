package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.ICotacaoDAO;
import models.Cotacao;

public class CotacaoDAO implements ICotacaoDAO {
    public void incluir(Cotacao cotacao) {
		incluir(cotacao, false);
	}
	
	public long incluir(Cotacao cotacao, boolean retornaId) {
		if (cotacao == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO cotacao (id_indice_remuneracao, data_cotacao, valor)
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
			
			pstm.setLong(1, cotacao.getIdIndiceRemuneracao());
			pstm.setDate(2, cotacao.getDataCotacao());
			pstm.setDouble(3, cotacao.getValor());
			
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
			throw new RuntimeException("Problemas ao incluir cotacao:\n" + e.getMessage());
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

	public void atualizar(Cotacao cotacao) {
		if (cotacao == null)
			return;
		
		final String comando =	"""
                                UPDATE cotacao
			                    SET id_indice_remuneracao = ?, data_cotacao = ?, valor = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, cotacao.getIdIndiceRemuneracao());
			pstm.setDate(2, cotacao.getDataCotacao());
			pstm.setDouble(3, cotacao.getValor());
			pstm.setLong(4, cotacao.getId());
			
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar cotacao:\n" + e.getMessage());
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
                                DELETE FROM cotacao
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
			throw new RuntimeException("Problemas ao excluir cotacao:\n" + e.getMessage());
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
	
	public Cotacao consultarPorId(long id) {
		final String comando =	"""
                                SELECT id_indice_remuneracao, data_cotacao, valor
				                FROM cotacao
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Cotacao        cotacao;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				cotacao = new Cotacao();
				
				cotacao.setId(id);
				cotacao.setIdIndiceRemuneracao(rs.getLong("id_indice_remuneracao"));
				cotacao.setDataCotacao(rs.getDate("data_cotacao"));
				cotacao.setValor(rs.getDouble("valor"));
				
				return cotacao;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar cotacao:\n" + e.getMessage());
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

    public Cotacao consultarPorIndiceRemuneracao(long idIndiceRemuneracao) {
		final String comando =	"""
                                SELECT id, data_cotacao, valor
				                FROM cotacao
                			    WHERE id_indice_remuneracao = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Cotacao        cotacao;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, idIndiceRemuneracao);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				cotacao = new Cotacao();
				
				cotacao.setId(rs.getLong("id"));
				cotacao.setDataCotacao(rs.getDate("data_cotacao"));
				cotacao.setValor(rs.getDouble("valor"));
                cotacao.setIdIndiceRemuneracao(idIndiceRemuneracao);
				
				return cotacao;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar cotacao:\n" + e.getMessage());
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

	public List<Cotacao> consultarTodos() {
		final String comando =	"""
                                SELECT id, id_indice_remuneracao, data_cotacao, valor
	                            FROM cotacao
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Cotacao        cotacao;
		List<Cotacao>  listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				cotacao = new Cotacao();
                listaRetorno = new ArrayList<>();
				
				cotacao.setId(rs.getLong("id"));
				cotacao.setIdIndiceRemuneracao(rs.getLong("id_indice_remuneracao"));
				cotacao.setDataCotacao(rs.getDate("data_cotacao"));
				cotacao.setValor(rs.getDouble("valor"));

				listaRetorno.add(cotacao);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar cotacao:\n" + e.getMessage());
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
