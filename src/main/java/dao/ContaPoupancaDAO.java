package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.MySQL;
import interfaces.IContaPoupancaDAO;
import models.ContaPoupanca;

public class ContaPoupancaDAO implements IContaPoupancaDAO {
    public void incluir(ContaPoupanca contaPoupanca) {
		incluir(contaPoupanca, false);
	}
	
	public long incluir(ContaPoupanca contaPoupanca, boolean retornaId) {
		if (contaPoupanca == null)
			return -1;
		
		final String comandoContaBancaria = """
											INSERT INTO conta_bancaria (codigo_banco, num_agencia, num_conta, saldo, data_abertura, id_titular)
											VALUES (?, ?, ?, ?, ?, ?)
											""";
		final String comandoContaPoupanca =	"""
											INSERT INTO conta_poupanca (id_conta_bancaria, id_indice_remuneracao, perc_rendimento_real)
											VALUES (?, ?, ?)
											""";
		Connection con = null;
		PreparedStatement pstmContaBancaria = null;
		PreparedStatement pstmContaPoupanca = null;
		Long idContaBancaria;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);
			
			pstmContaBancaria = con.prepareStatement(comandoContaBancaria, PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmContaBancaria.setLong(1, contaPoupanca.getCodigoBanco());
			pstmContaBancaria.setLong(2, contaPoupanca.getNumAgencia());
			pstmContaBancaria.setLong(3, contaPoupanca.getNumConta());
			pstmContaBancaria.setDouble(4, contaPoupanca.getSaldo());
			pstmContaBancaria.setDate(5, contaPoupanca.getDataAbertura());
			pstmContaBancaria.setLong(6, contaPoupanca.getIdTitular());
			
			pstmContaBancaria.execute();

			ResultSet rsContaBancaria = pstmContaBancaria.getGeneratedKeys();
			if (rsContaBancaria.next());
				idContaBancaria = rsContaBancaria.getLong(1);
				
			pstmContaPoupanca = con.prepareStatement(comandoContaPoupanca);
				
			pstmContaPoupanca.setLong(1, idContaBancaria);
			pstmContaPoupanca.setLong(2, contaPoupanca.getIdIndiceRemuneracao());
			pstmContaPoupanca.setDouble(3, contaPoupanca.getPercRedimentoReal());
				
			pstmContaPoupanca.execute();
				
			con.commit();

			if (!retornaId)
				idContaBancaria = -1L;

			return idContaBancaria;
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao incluir conta poupanca:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaPoupanca != null)
					pstmContaPoupanca.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void atualizar(ContaPoupanca contaPoupanca) {
		if (contaPoupanca == null)
			return;
		
		final String comandoContaBancaria =	"""
											UPDATE conta_bancaria
											SET codigo_banco = ?, num_agencia = ?, num_conta = ?, saldo = ?, data_abertura = ?, id_titular = ?
											WHERE id = ?
											""";
		final String comandoContaPoupanca =	"""
											UPDATE conta_poupanca
											SET id_indice_remuneracao = ?, perc_rendimento_real = ?
											WHERE id_conta_bancaria = ?
											""";
		Connection con = null;
		PreparedStatement pstmContaBancaria = null;
		PreparedStatement pstmContaPoupanca = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmContaBancaria = con.prepareStatement(comandoContaBancaria);

			pstmContaBancaria.setLong(1, contaPoupanca.getCodigoBanco());
			pstmContaBancaria.setLong(2, contaPoupanca.getNumAgencia());
			pstmContaBancaria.setLong(3, contaPoupanca.getNumConta());
			pstmContaBancaria.setDouble(4, contaPoupanca.getSaldo());
			pstmContaBancaria.setDate(5, contaPoupanca.getDataAbertura());
			pstmContaBancaria.setLong(6, contaPoupanca.getIdTitular());
			pstmContaBancaria.setLong(7, contaPoupanca.getId());

			pstmContaPoupanca = con.prepareStatement(comandoContaPoupanca);
			
			pstmContaPoupanca.setLong(1, contaPoupanca.getIdIndiceRemuneracao());
			pstmContaPoupanca.setDouble(2, contaPoupanca.getPercRedimentoReal());
			pstmContaPoupanca.setLong(3, contaPoupanca.getId());
			
			pstmContaPoupanca.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao atualizar conta poupanca:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaPoupanca != null)
					pstmContaPoupanca.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void excluir(long id) {
		final String comandoContaPoupanca =	"""
											DELETE FROM conta_poupanca
											WHERE id_conta_bancaria = ?
											""";
		final String comandoContaBancaria =	"""
											DELETE FROM conta_bancaria
											WHERE id = ?
											""";
		Connection        con = null;
		PreparedStatement pstmContaPoupanca = null;
		PreparedStatement pstmContaBancaria = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmContaPoupanca = con.prepareStatement(comandoContaPoupanca);
			pstmContaPoupanca.setLong(1, id);
			pstmContaPoupanca.execute();
			
			pstmContaBancaria = con.prepareStatement(comandoContaBancaria);
			pstmContaBancaria.setLong(1, id);
			pstmContaBancaria.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao excluir conta poupanca:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaPoupanca != null)
					pstmContaPoupanca.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
	
	public ContaPoupanca consultarPorId(long id) {
		final String comando =	"""
                                SELECT cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cp.id_indice_remuneracao, cp.perc_rendimento_real
				                FROM conta_poupanca cp
								INNER JOIN conta_bancaria cb
									ON cp.id_conta_bancaria = cb.id
                			    WHERE cp.id_conta_bancaria = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
        ContaPoupanca     contaPoupanca = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
                contaPoupanca = new ContaPoupanca();

				contaPoupanca.setId(id);
                contaPoupanca.setCodigoBanco(rs.getLong("codigo_banco"));
				contaPoupanca.setNumAgencia(rs.getLong("num_agencia"));
				contaPoupanca.setNumConta(rs.getLong("num_conta"));
				contaPoupanca.depositar(rs.getDouble("saldo"));
				contaPoupanca.setDataAbertura(rs.getDate("data_abertura"));
				contaPoupanca.setIdTitular(rs.getLong("id_titular"));
				contaPoupanca.setIdIndiceRemuneracao(rs.getLong("id_indice_remuneracao"));
                contaPoupanca.setPercRedimentoReal(rs.getDouble("perc_rendimento_real"));

				return contaPoupanca;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta poupanca:\n" + e.getMessage());
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

	public List<ContaPoupanca> consultarPorTitular(long idTitular) {
		final String comando =	"""
                                SELECT cp.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura,
								cp.id_indice_remuneracao, cp.perc_rendimento_real
				                FROM conta_poupanca cp
								INNER JOIN conta_bancaria cb
									ON cp.id_conta_bancaria = cb.id
                			    WHERE cb.id_titular = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaPoupanca       contaPoupanca;
		List<ContaPoupanca> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, idTitular);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaPoupanca = new ContaPoupanca();
                listaRetorno = new ArrayList<>();
				
				contaPoupanca.setId(rs.getLong("id_conta_bancaria"));
                contaPoupanca.setCodigoBanco(rs.getLong("codigo_banco"));
				contaPoupanca.setNumAgencia(rs.getLong("num_agencia"));
				contaPoupanca.setNumConta(rs.getLong("num_conta"));
				contaPoupanca.depositar(rs.getDouble("saldo"));
				contaPoupanca.setDataAbertura(rs.getDate("data_abertura"));
				contaPoupanca.setIdTitular(idTitular);
				contaPoupanca.setIdIndiceRemuneracao(rs.getLong("id_indice_remuneracao"));
                contaPoupanca.setPercRedimentoReal(rs.getDouble("perc_rendimento_real"));

				listaRetorno.add(contaPoupanca);
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta poupanca:\n" + e.getMessage());
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

    public List<ContaPoupanca> consultarPorBanco(long codigoBanco) {
		final String comando =	"""
                                SELECT cp.id_conta_bancaria, cb.num_agencia, cb.num_saldo, cb.saldo, cb.data_abertura, cb.id_titular
								cp.id_indice_remuneracao, cp.perc_rendimento_real
				                FROM conta_poupanca cp
								INNER JOIN conta_bancaria cb
									ON cp.id_conta_bancaria = cb.id
                			    WHERE cb.codigo_banco = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaPoupanca       contaPoupanca;
		List<ContaPoupanca> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, codigoBanco);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaPoupanca = new ContaPoupanca();
                listaRetorno = new ArrayList<>();
				
				contaPoupanca.setId(rs.getLong("id_conta_bancaria"));
                contaPoupanca.setCodigoBanco(rs.getLong("codigo_banco"));
				contaPoupanca.setNumAgencia(rs.getLong("num_agencia"));
				contaPoupanca.setNumConta(rs.getLong("num_conta"));
				contaPoupanca.depositar(rs.getDouble("saldo"));
				contaPoupanca.setDataAbertura(rs.getDate("data_abertura"));
				contaPoupanca.setIdTitular(rs.getLong("id_titular"));
				contaPoupanca.setIdIndiceRemuneracao(rs.getLong("id_indice_remuneracao"));
                contaPoupanca.setPercRedimentoReal(rs.getDouble("perc_rendimento_real"));

				listaRetorno.add(contaPoupanca);
			}

			return listaRetorno;
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao consultar conta poupanca:\n" + e.getMessage());
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

	public List<ContaPoupanca> consultarTodos() {
		final String comando =	"""
                                SELECT cp.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cp.id_indice_remuneracao, cp.perc_rendimento_real
				                FROM conta_poupanca cp
								INNER JOIN conta_bancaria cb
									ON cp.id_conta_bancaria = cb.id
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaPoupanca       contaPoupanca;
		List<ContaPoupanca> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaPoupanca = new ContaPoupanca();
                listaRetorno = new ArrayList<>();
				
				contaPoupanca.setId(rs.getLong("id_conta_bancaria"));
                contaPoupanca.setCodigoBanco(rs.getLong("codigo_banco"));
				contaPoupanca.setNumAgencia(rs.getLong("num_agencia"));
				contaPoupanca.setNumConta(rs.getLong("num_conta"));
				contaPoupanca.depositar(rs.getDouble("saldo"));
				contaPoupanca.setDataAbertura(rs.getDate("data_abertura"));
				contaPoupanca.setIdTitular(rs.getLong("id_titular"));
				contaPoupanca.setIdIndiceRemuneracao(rs.getLong("id_indice_remuneracao"));
                contaPoupanca.setPercRedimentoReal(rs.getDouble("perc_rendimento_real"));

				listaRetorno.add(contaPoupanca);				
			}

			return listaRetorno;
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao consultar conta poupanca:\n" + e.getMessage());
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
