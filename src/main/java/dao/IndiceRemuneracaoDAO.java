package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import enums.Situacao;
import interfaces.IIndiceRemuneracaoDAO;
import models.IndiceRemuneracao;

public class IndiceRemuneracaoDAO implements IIndiceRemuneracaoDAO {
    public void incluir(IndiceRemuneracao indiceRemuneracao) {
		incluir(indiceRemuneracao, false);
	}
	
	public long incluir(IndiceRemuneracao indiceRemuneracao, boolean retornaId) {
		if (indiceRemuneracao == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO indice_remuneracao (descricao, periodicidade, situacao)
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
			
			pstm.setString(1, indiceRemuneracao.getDescricao());
			pstm.setInt(2, indiceRemuneracao.getPeriodicidade());
			pstm.setInt(3, indiceRemuneracao.getSituacao().getId());
			
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
			throw new RuntimeException("Problemas ao incluir indice remuneracao:\n" + e.getMessage());
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

	public void atualizar(IndiceRemuneracao indiceRemuneracao) {
		if (indiceRemuneracao == null)
			return;
		
		final String comando =	"""
                                UPDATE indice_remuneracao
			                    SET descricao = ?, periodicidade = ?, situacao = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, indiceRemuneracao.getDescricao());
			pstm.setInt(2, indiceRemuneracao.getPeriodicidade());
			pstm.setInt(3, indiceRemuneracao.getSituacao().getId());
			pstm.setLong(4, indiceRemuneracao.getId());
			
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar indice remuneracao:\n" + e.getMessage());
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
                                DELETE FROM indice_remuneracao
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
			throw new RuntimeException("Problemas ao excluir indice remuneracao:\n" + e.getMessage());
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
	
	public IndiceRemuneracao consultarPorId(long id) {
		final String comando =	"""
                                SELECT descricao, periodicidade, situacao
				                FROM indice_remuneracao
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		IndiceRemuneracao        indiceRemuneracao;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				indiceRemuneracao = new IndiceRemuneracao();
				
				indiceRemuneracao.setId(id);
				indiceRemuneracao.setDescricao(rs.getString("descricao"));
				indiceRemuneracao.setPeriodicidade(rs.getInt("periodicidade"));
				indiceRemuneracao.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				
				return indiceRemuneracao;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar indice remuneracao:\n" + e.getMessage());
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

	public IndiceRemuneracao consultarPorSituacao(Situacao situacao) {
		final String comando =	"""
                                SELECT id, descricao, periodicidade
				                FROM indice_remuneracao
                			    WHERE situacao = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		IndiceRemuneracao        indiceRemuneracao;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setInt(1, situacao.getId());
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				indiceRemuneracao = new IndiceRemuneracao();
				
				indiceRemuneracao.setId(rs.getLong("id"));
				indiceRemuneracao.setDescricao(rs.getString("descricao"));
				indiceRemuneracao.setPeriodicidade(rs.getInt("periodicidade"));
				indiceRemuneracao.setSituacao(situacao);
				
				return indiceRemuneracao;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar indice remuneracao:\n" + e.getMessage());
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

	public List<IndiceRemuneracao> consultarTodos() {
		final String comando =	"""
                                SELECT id, descricao, periodicidade, situacao
	                            FROM indice_remuneracao
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		IndiceRemuneracao        indiceRemuneracao;
		List<IndiceRemuneracao>  listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				indiceRemuneracao = new IndiceRemuneracao();
                listaRetorno = new ArrayList<>();
				
				indiceRemuneracao.setId(rs.getLong("id"));
				indiceRemuneracao.setDescricao(rs.getString("descricao"));
				indiceRemuneracao.setPeriodicidade(rs.getInt("periodicidade"));
				indiceRemuneracao.setSituacao(Situacao.fromId(rs.getInt("situacao")));

				listaRetorno.add(indiceRemuneracao);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar indice remuneracao:\n" + e.getMessage());
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
