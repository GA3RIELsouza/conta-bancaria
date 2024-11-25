package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.IContaCorrenteDAO;
import models.ContaCorrente;

public class ContaCorrenteDAO implements IContaCorrenteDAO {
    public void incluir(ContaCorrente contaCorrente) {
		incluir(contaCorrente, false);
	}
	
	public long incluir(ContaCorrente contaCorrente, boolean retornaId) {
		if (contaCorrente == null)
			return -1;
		
		final String comandoContaBancaria = """
											INSERT INTO conta_bancaria (codigo_banco, num_agencia, num_conta, saldo, data_abertura, id_titular)
											VALUES (?, ?, ?, ?, ?, ?)
											""";
		final String comandoContaCorrente =	"""
											INSERT INTO conta_corrente (id_conta_bancaria, valor_cesta_servicos, limite_pix_noturno)
											VALUES (?, ?, ?)
											""";
		Connection con = null;
		PreparedStatement pstmContaBancaria = null;
		PreparedStatement pstmContaCorrente = null;
		Long idContaBancaria;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);
			
			pstmContaBancaria = con.prepareStatement(comandoContaBancaria, PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmContaBancaria.setLong(1, contaCorrente.getCodigoBanco());
			pstmContaBancaria.setLong(2, contaCorrente.getNumAgencia());
			pstmContaBancaria.setLong(3, contaCorrente.getNumConta());
			pstmContaBancaria.setDouble(4, contaCorrente.getSaldo());
			pstmContaBancaria.setDate(5, contaCorrente.getDataAbertura());
			pstmContaBancaria.setLong(6, contaCorrente.getIdTitular());
			
			pstmContaBancaria.execute();

			ResultSet rsContaBancaria = pstmContaBancaria.getGeneratedKeys();
			if (rsContaBancaria.next());
				idContaBancaria = rsContaBancaria.getLong(1);
				
			pstmContaCorrente = con.prepareStatement(comandoContaCorrente);
				
			pstmContaCorrente.setLong(1, idContaBancaria);
			pstmContaCorrente.setDouble(2, contaCorrente.getValorCestaServicos());
			pstmContaCorrente.setDouble(3, contaCorrente.getLimitePixNoturno());
				
			pstmContaCorrente.execute();
				
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

			throw new RuntimeException("Problemas ao incluir conta corrente:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaCorrente != null)
					pstmContaCorrente.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void atualizar(ContaCorrente contaCorrente) {
		if (contaCorrente == null)
			return;
		
		final String comandoContaBancaria =	"""
											UPDATE conta_bancaria
											SET codigo_banco = ?, num_agencia = ?, num_conta = ?, saldo = ?, data_abertura = ?, id_titular = ?
											WHERE id = ?
											""";
		final String comandoContaCorrente =	"""
											UPDATE conta_corrente
											SET valor_cesta_servicos = ?, limite_pix_noturno = ?
											WHERE id_conta_bancaria = ?
											""";
		Connection con = null;
		PreparedStatement pstmContaBancaria = null;
		PreparedStatement pstmContaCorrente = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmContaBancaria = con.prepareStatement(comandoContaBancaria);

			pstmContaBancaria.setLong(1, contaCorrente.getCodigoBanco());
			pstmContaBancaria.setLong(2, contaCorrente.getNumAgencia());
			pstmContaBancaria.setLong(3, contaCorrente.getNumConta());
			pstmContaBancaria.setDouble(4, contaCorrente.getSaldo());
			pstmContaBancaria.setDate(5, contaCorrente.getDataAbertura());
			pstmContaBancaria.setLong(6, contaCorrente.getIdTitular());
			pstmContaBancaria.setLong(7, contaCorrente.getId());

			pstmContaCorrente = con.prepareStatement(comandoContaCorrente);
			
			pstmContaCorrente.setDouble(1, contaCorrente.getValorCestaServicos());
			pstmContaCorrente.setDouble(2, contaCorrente.getLimitePixNoturno());
			pstmContaCorrente.setLong(3, contaCorrente.getId());
			
			pstmContaCorrente.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao atualizar conta corrente:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaCorrente != null)
					pstmContaCorrente.close();
				
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
		final String comandoContaCorrente =	"""
											DELETE FROM conta_corrente
											WHERE id_conta_bancaria = ?
											""";
		final String comandoContaBancaria =	"""
											DELETE FROM conta_bancaria
											WHERE id = ?
											""";
		Connection        con = null;
		PreparedStatement pstmContaCorrente = null;
		PreparedStatement pstmContaBancaria = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmContaCorrente = con.prepareStatement(comandoContaCorrente);
			pstmContaCorrente.setLong(1, id);
			pstmContaCorrente.execute();
			
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

			throw new RuntimeException("Problemas ao excluir conta corrente:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaCorrente != null)
					pstmContaCorrente.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
	
	public ContaCorrente consultarPorId(long id) {
		final String comando =	"""
                                SELECT cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_corrente cc
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                			    WHERE cc.id_conta_bancaria = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
        ContaCorrente     contaCorrente = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
                contaCorrente = new ContaCorrente();

				contaCorrente.setId(id);
                contaCorrente.setCodigoBanco(rs.getLong("codigo_banco"));
				contaCorrente.setNumAgencia(rs.getLong("num_agencia"));
				contaCorrente.setNumConta(rs.getLong("num_conta"));
				contaCorrente.depositar(rs.getDouble("saldo"));
				contaCorrente.setDataAbertura(rs.getDate("data_abertura"));
				contaCorrente.setIdTitular(rs.getLong("id_titular"));
				contaCorrente.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaCorrente.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));

				return contaCorrente;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta corrente:\n" + e.getMessage());
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

	public List<ContaCorrente> consultarPorTitular(long idTitular) {
		final String comando =	"""
                                SELECT cc.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_corrente cc
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                			    WHERE cb.id_titular = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaCorrente       contaCorrente;
		List<ContaCorrente> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, idTitular);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaCorrente = new ContaCorrente();
                listaRetorno = new ArrayList<>();
				
				contaCorrente.setId(rs.getLong("id_conta_bancaria"));
                contaCorrente.setCodigoBanco(rs.getLong("codigo_banco"));
				contaCorrente.setNumAgencia(rs.getLong("num_agencia"));
				contaCorrente.setNumConta(rs.getLong("num_conta"));
				contaCorrente.depositar(rs.getDouble("saldo"));
				contaCorrente.setDataAbertura(rs.getDate("data_abertura"));
				contaCorrente.setIdTitular(idTitular);
				contaCorrente.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaCorrente.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));

				listaRetorno.add(contaCorrente);
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta corrente:\n" + e.getMessage());
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

    public List<ContaCorrente> consultarPorBanco(long codigoBanco) {
		final String comando =	"""
                                SELECT cc.id_conta_bancaria, cb.num_agencia, cb.num_saldo, cb.saldo, cb.data_abertura, cb.id_titular
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_corrente cc
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                			    WHERE cb.codigo_banco = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaCorrente       contaCorrente;
		List<ContaCorrente> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, codigoBanco);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaCorrente = new ContaCorrente();
                listaRetorno = new ArrayList<>();
				
				contaCorrente.setId(rs.getLong("id_conta_bancaria"));
                contaCorrente.setCodigoBanco(codigoBanco);
				contaCorrente.setNumAgencia(rs.getLong("num_agencia"));
				contaCorrente.setNumConta(rs.getLong("num_conta"));
				contaCorrente.depositar(rs.getDouble("saldo"));
				contaCorrente.setDataAbertura(rs.getDate("data_abertura"));
				contaCorrente.setIdTitular(rs.getLong("id_titular"));
				contaCorrente.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaCorrente.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));

				listaRetorno.add(contaCorrente);
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

			throw new RuntimeException("Problemas ao consultar conta corrente:\n" + e.getMessage());
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

	public List<ContaCorrente> consultarTodos() {
		final String comando =	"""
                                SELECT cc.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_corrente cc
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaCorrente       contaCorrente;
		List<ContaCorrente> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaCorrente = new ContaCorrente();
                listaRetorno = new ArrayList<>();
				
				contaCorrente.setId(rs.getLong("id_conta_bancaria"));
                contaCorrente.setCodigoBanco(rs.getLong("codigo_banco"));
				contaCorrente.setNumAgencia(rs.getLong("num_agencia"));
				contaCorrente.setNumConta(rs.getLong("num_conta"));
				contaCorrente.depositar(rs.getDouble("saldo"));
				contaCorrente.setDataAbertura(rs.getDate("data_abertura"));
				contaCorrente.setIdTitular(rs.getLong("id_titular"));
				contaCorrente.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaCorrente.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));

				listaRetorno.add(contaCorrente);				
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

			throw new RuntimeException("Problemas ao consultar conta corrente:\n" + e.getMessage());
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
