package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import enums.Situacao;
import enums.TipoMov;
import interfaces.IEventoDAO;
import models.Evento;

public class EventoDAO implements IEventoDAO {
    public void incluir(Evento evento) {
		incluir(evento, false);
	}
	
	public long incluir(Evento evento, boolean retornaCodigo) {
		if (evento == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO evento (descricao, tipo_movimentacao, situacao)
                                VALUES (?, ?, ?)
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
			
            pstm.setString(1, evento.getDescricao());
			pstm.setInt(2, evento.getTipoMovimentacao().getId());
			pstm.setInt(3, evento.getSituacao().getId());
			
			pstm.execute();
			
			if (retornaCodigo) {
				ResultSet rs = pstm.getGeneratedKeys();
				rs.next();
				codigo = rs.getLong(1);
			}
			else
				codigo = (long) -1;

			return codigo;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir evento:\n" + e.getMessage());
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

	public void atualizar(Evento evento) {
		if (evento == null)
			return;
		
		final String comando =	"""
                                UPDATE evento
			                    SET descricao = ?, tipo_movimentacao = ?, situacao = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, evento.getDescricao());
			pstm.setInt(2, evento.getTipoMovimentacao().getId());
			pstm.setInt(3, evento.getSituacao().getId());
			pstm.setLong(4, evento.getId());
			
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar evento:\n" + e.getMessage());
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
                                DELETE FROM evento
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
			throw new RuntimeException("Problemas ao excluir evento:\n" + e.getMessage());
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
	
	public Evento consultarPorId(long id) {
		final String comando =	"""
                                SELECT descricao, tipo_movimentacao, situacao
				                FROM evento
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Evento            evento;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				evento = new Evento();
				
				evento.setId(id);
				evento.setDescricao(rs.getString("descricao"));
				evento.setTipoMovimentacao(TipoMov.fromId(rs.getInt("tipo_movimentacao")));
				evento.setSituacao(Situacao.fromId(rs.getInt("situacao")));

				return evento;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar evento:\n" + e.getMessage());
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

	public Evento consultarPorSituacao(Situacao situacao) {
		final String comando =	"""
                                SELECT id, descricao, tipo_movimentacao
				                FROM evento
                			    WHERE situacao = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Evento            evento;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setInt(1, situacao.getId());
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				evento = new Evento();
				
				evento.setId(rs.getLong("id"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setTipoMovimentacao(TipoMov.fromId(rs.getInt("tipo_movimentacao")));
				evento.setSituacao(situacao);

				return evento;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar evento:\n" + e.getMessage());
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

	public List<Evento> consultarTodos() {
		final String comando =	"""
                                SELECT id, descricao, tipo_movimentacao, situacao
	                            FROM evento
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Evento            evento;
		List<Evento>      listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				evento = new Evento();
                listaRetorno = new ArrayList<>();
				
				evento.setId(rs.getLong("id"));
				evento.setDescricao(rs.getString("descricao"));
				evento.setTipoMovimentacao(TipoMov.fromId(rs.getInt("tipo_movimentacao")));
				evento.setSituacao(Situacao.fromId(rs.getInt("situacao")));

				listaRetorno.add(evento);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar evento:\n" + e.getMessage());
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
