package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.IBancoDAO;
import models.Banco;

public class BancoDAO implements IBancoDAO {
    public void incluir(Banco banco) {
		incluir(banco, false);
	}
	
	public long incluir(Banco banco, boolean retornaCodigo) {
		if (banco == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO banco (codigo, nome, mascara_agencia, mascara_conta)
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
			
            pstm.setLong(1, banco.getCodigo());
			pstm.setString(2, banco.getNome());
			pstm.setString(3, banco.getMascaraAgencia());
			pstm.setString(4, banco.getMascaraConta());
			
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
			throw new RuntimeException("Problemas ao incluir banco:\n" + e.getMessage());
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

	public void atualizar(Banco banco) {
		if (banco == null)
			return;
		
		final String comando =	"""
                                UPDATE banco
			                    SET nome = ?, mascara_agencia = ?, mascara_conta = ?
		                        WHERE codigo = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, banco.getNome());
			pstm.setString(2, banco.getMascaraAgencia());
			pstm.setString(3, banco.getMascaraConta());
			pstm.setLong(4, banco.getCodigo());
			
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar banco:\n" + e.getMessage());
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

	public void excluir(long codigo) {
		final String comando =	"""
                                DELETE FROM banco
		                        WHERE codigo = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, codigo);

			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao excluir banco:\n" + e.getMessage());
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
	
	public Banco consultarPorId(long codigo) {
		final String comando =	"""
                                SELECT nome, mascara_agencia, mascara_conta
				                FROM banco
                			    WHERE codigo = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Banco             banco;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, codigo);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				banco = new Banco();
				
				banco.setCodigo(codigo);
				banco.setNome(rs.getString("nome"));
				banco.setMascaraAgencia(rs.getString("mascara_agencia"));
				banco.setMascaraConta(rs.getString("mascara_conta"));

				return banco;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar banco:\n" + e.getMessage());
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

	public List<Banco> consultarTodos() {
		final String comando =	"""
                                SELECT codigo, nome, mascara_agencia, mascara_conta
	                            FROM banco
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		Banco        banco;
		List<Banco>  listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				banco = new Banco();
                listaRetorno = new ArrayList<>();
				
				banco.setCodigo(rs.getLong("codigo"));
				banco.setNome(rs.getString("nome"));
				banco.setMascaraAgencia(rs.getString("mascara_agencia"));
				banco.setMascaraConta(rs.getString("mascara_conta"));

				listaRetorno.add(banco);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar banco:\n" + e.getMessage());
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
