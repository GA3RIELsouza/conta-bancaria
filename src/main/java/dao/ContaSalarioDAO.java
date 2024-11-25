package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.IContaSalarioDAO;
import models.ContaSalario;

public class ContaSalarioDAO implements IContaSalarioDAO {
    public void incluir(ContaSalario contaSalario) {
		incluir(contaSalario, false);
	}
	
	public long incluir(ContaSalario contaSalario, boolean retornaId) {
		if (contaSalario == null)
			return -1;
		
		final String comandoContaBancaria = """
											INSERT INTO conta_bancaria (codigo_banco, num_agencia, num_conta, saldo, data_abertura, id_titular)
											VALUES (?, ?, ?, ?, ?, ?)
											""";
		final String comandoContaCorrente =	"""
											INSERT INTO conta_salario (id_conta_bancaria, cnpj_vinculado, limite_consignado,
                                            limite_antecipacao_mes, permite_antecipar_13o, id_conta_vinculada)
											VALUES (?, ?, ?, ?, ?, ?)
											""";
		Connection con = null;
		PreparedStatement pstmContaBancaria = null;
		PreparedStatement pstmContaSalario = null;
		Long idContaBancaria;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);
			
			pstmContaBancaria = con.prepareStatement(comandoContaBancaria, PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstmContaBancaria.setLong(1, contaSalario.getCodigoBanco());
			pstmContaBancaria.setLong(2, contaSalario.getNumAgencia());
			pstmContaBancaria.setLong(3, contaSalario.getNumConta());
			pstmContaBancaria.setDouble(4, contaSalario.getSaldo());
			pstmContaBancaria.setDate(5, contaSalario.getDataAbertura());
			pstmContaBancaria.setLong(6, contaSalario.getIdTitular());
			
			pstmContaBancaria.execute();

			ResultSet rsContaBancaria = pstmContaBancaria.getGeneratedKeys();
			if (rsContaBancaria.next());
				idContaBancaria = rsContaBancaria.getLong(1);
				
			pstmContaSalario = con.prepareStatement(comandoContaCorrente);
				
			pstmContaSalario.setLong(1, idContaBancaria);
			pstmContaSalario.setString(2, contaSalario.getCnpjVinculado());
			pstmContaSalario.setDouble(3, contaSalario.getLimiteConsignado());
            pstmContaSalario.setDouble(4, contaSalario.getLimiteAntecipadoMes());
            pstmContaSalario.setBoolean(5, contaSalario.isPermiteAntecipar13o());
            pstmContaSalario.setLong(6, contaSalario.getIdContaVinculada());
				
			pstmContaSalario.execute();
				
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

			throw new RuntimeException("Problemas ao incluir conta salario:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaSalario != null)
					pstmContaSalario.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void atualizar(ContaSalario contaSalario) {
		if (contaSalario == null)
			return;
		
		final String comandoContaBancaria =	"""
											UPDATE conta_bancaria
											SET codigo_banco = ?, num_agencia = ?, num_conta = ?, saldo = ?, data_abertura = ?, id_titular = ?
											WHERE id = ?
											""";
		final String comandoContaCorrente =	"""
											UPDATE conta_salario
											SET cnpj_vinculado = ?, limite_consignado = ?, limite_antecipacao_mes = ?, permite_antecipar_13o = ?, id_conta_vinculada = ?
											WHERE id_conta_bancaria = ?
											""";
		Connection con = null;
		PreparedStatement pstmContaBancaria = null;
		PreparedStatement pstmContaSalario = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmContaBancaria = con.prepareStatement(comandoContaBancaria);

			pstmContaBancaria.setLong(1, contaSalario.getCodigoBanco());
			pstmContaBancaria.setLong(2, contaSalario.getNumAgencia());
			pstmContaBancaria.setLong(3, contaSalario.getNumConta());
			pstmContaBancaria.setDouble(4, contaSalario.getSaldo());
			pstmContaBancaria.setDate(5, contaSalario.getDataAbertura());
			pstmContaBancaria.setLong(6, contaSalario.getIdTitular());
			pstmContaBancaria.setLong(7, contaSalario.getId());

			pstmContaSalario = con.prepareStatement(comandoContaCorrente);
			
			pstmContaSalario.setString(1, contaSalario.getCnpjVinculado());
			pstmContaSalario.setDouble(2, contaSalario.getLimiteConsignado());
            pstmContaSalario.setDouble(3, contaSalario.getLimiteAntecipadoMes());
            pstmContaSalario.setBoolean(4, contaSalario.isPermiteAntecipar13o());
            pstmContaSalario.setLong(5, contaSalario.getIdContaVinculada());
            pstmContaSalario.setLong(6, contaSalario.getId());
			
			pstmContaSalario.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao atualizar conta salario:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaSalario != null)
					pstmContaSalario.close();
				
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
											DELETE FROM conta_salario
											WHERE id_conta_bancaria = ?
											""";
		final String comandoContaBancaria =	"""
											DELETE FROM conta_bancaria
											WHERE id = ?
											""";
		Connection        con = null;
		PreparedStatement pstmContaSalario = null;
		PreparedStatement pstmContaBancaria = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmContaSalario = con.prepareStatement(comandoContaCorrente);
			pstmContaSalario.setLong(1, id);
			pstmContaSalario.execute();
			
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

			throw new RuntimeException("Problemas ao excluir conta salario:\n" + e.getMessage());
		} finally {
			try {
				if (pstmContaBancaria != null)
					pstmContaBancaria.close();

				if (pstmContaSalario != null)
					pstmContaSalario.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
	
	public ContaSalario consultarPorId(long id) {
		final String comando =	"""
                                SELECT cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cs.cnpj_vinculado, cs.limite_consignado, cs.limite_antecipacao_mes, cs.permite_antecipar_13o, cs.id_conta_vinculada
				                FROM conta_salario cs
								INNER JOIN conta_bancaria cb
									ON cs.id_conta_bancaria = cb.id
                			    WHERE cs.id_conta_bancaria = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
        ContaSalario     contaSalario = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
                contaSalario = new ContaSalario();

				contaSalario.setId(id);
                contaSalario.setCodigoBanco(rs.getLong("codigo_banco"));
				contaSalario.setNumAgencia(rs.getLong("num_agencia"));
				contaSalario.setNumConta(rs.getLong("num_conta"));
				contaSalario.depositar(rs.getDouble("saldo"));
				contaSalario.setDataAbertura(rs.getDate("data_abertura"));
				contaSalario.setIdTitular(rs.getLong("id_titular"));
				contaSalario.setCnpjVinculado(rs.getString("cnpj_vinculado"));
                contaSalario.setLimiteConsignado(rs.getDouble("limite_consignado"));
                contaSalario.setLimiteAntecipadoMes(rs.getDouble("limite_antecipacao_mes"));
                contaSalario.setPermiteAntecipar13o(rs.getBoolean("permite_antecipar_13o"));
                contaSalario.setIdContaVinculada(rs.getLong("id_conta_vinculada"));

				return contaSalario;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta salario:\n" + e.getMessage());
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

	public List<ContaSalario> consultarPorTitular(long idTitular) {
		final String comando =	"""
                                SELECT cs.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura,
								cs.cnpj_vinculado, cs.limite_consignado, cs.limite_antecipacao_mes, cs.permite_antecipar_13o, cs.id_conta_vinculada
				                FROM conta_salario cs
								INNER JOIN conta_bancaria cb
									ON cs.id_conta_bancaria = cb.id
                			    WHERE cb.id_titular = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaSalario       contaSalario;
		List<ContaSalario> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, idTitular);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaSalario = new ContaSalario();
                listaRetorno = new ArrayList<>();
				
				contaSalario.setId(rs.getLong("id_conta_bancaria"));
                contaSalario.setCodigoBanco(rs.getLong("codigo_banco"));
				contaSalario.setNumAgencia(rs.getLong("num_agencia"));
				contaSalario.setNumConta(rs.getLong("num_conta"));
				contaSalario.depositar(rs.getDouble("saldo"));
				contaSalario.setDataAbertura(rs.getDate("data_abertura"));
				contaSalario.setIdTitular(idTitular);
				contaSalario.setCnpjVinculado(rs.getString("cnpj_vinculado"));
                contaSalario.setLimiteConsignado(rs.getDouble("limite_consignado"));
                contaSalario.setLimiteAntecipadoMes(rs.getDouble("limite_antecipacao_mes"));
                contaSalario.setPermiteAntecipar13o(rs.getBoolean("permite_antecipar_13o"));
                contaSalario.setIdContaVinculada(rs.getLong("id_conta_vinculada"));

				listaRetorno.add(contaSalario);
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta salario:\n" + e.getMessage());
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

    public List<ContaSalario> consultarPorCnpj(String cnpj) {
        final String comando =	"""
                                SELECT cs.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cs.limite_consignado, cs.limite_antecipacao_mes, cs.permite_antecipar_13o, cs.id_conta_vinculada
				                FROM conta_salario cs
								INNER JOIN conta_bancaria cb
									ON cs.id_conta_bancaria = cb.id
                			    WHERE cs.cnpj_vinculado = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaSalario       contaSalario;
		List<ContaSalario> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setString(1, cnpj);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaSalario = new ContaSalario();
                listaRetorno = new ArrayList<>();
				
				contaSalario.setId(rs.getLong("id_conta_bancaria"));
                contaSalario.setCodigoBanco(rs.getLong("codigo_banco"));
				contaSalario.setNumAgencia(rs.getLong("num_agencia"));
				contaSalario.setNumConta(rs.getLong("num_conta"));
				contaSalario.depositar(rs.getDouble("saldo"));
				contaSalario.setDataAbertura(rs.getDate("data_abertura"));
				contaSalario.setIdTitular(rs.getLong("id_titular"));
				contaSalario.setCnpjVinculado(cnpj);
                contaSalario.setLimiteConsignado(rs.getDouble("limite_consignado"));
                contaSalario.setLimiteAntecipadoMes(rs.getDouble("limite_antecipacao_mes"));
                contaSalario.setPermiteAntecipar13o(rs.getBoolean("permite_antecipar_13o"));
                contaSalario.setIdContaVinculada(rs.getLong("id_conta_vinculada"));

				listaRetorno.add(contaSalario);
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

			throw new RuntimeException("Problemas ao consultar conta salario:\n" + e.getMessage());
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

    public List<ContaSalario> consultarPorBanco(long codigoBanco) {
		final String comando =	"""
                                SELECT cs.id_conta_bancaria, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cs.cnpj_vinculado, cs.limite_consignado, cs.limite_antecipacao_mes, cs.permite_antecipar_13o, cs.id_conta_vinculada
				                FROM conta_salario cs
								INNER JOIN conta_bancaria cb
									ON cs.id_conta_bancaria = cb.id
                			    WHERE cb.codigo_banco = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaSalario       contaSalario;
		List<ContaSalario> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, codigoBanco);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaSalario = new ContaSalario();
                listaRetorno = new ArrayList<>();
				
				contaSalario.setId(rs.getLong("id_conta_bancaria"));
                contaSalario.setCodigoBanco(codigoBanco);
				contaSalario.setNumAgencia(rs.getLong("num_agencia"));
				contaSalario.setNumConta(rs.getLong("num_conta"));
				contaSalario.depositar(rs.getDouble("saldo"));
				contaSalario.setDataAbertura(rs.getDate("data_abertura"));
				contaSalario.setIdTitular(rs.getLong("id_titular"));
				contaSalario.setCnpjVinculado(rs.getString("cnpj_vinculado"));
                contaSalario.setLimiteConsignado(rs.getDouble("limite_consignado"));
                contaSalario.setLimiteAntecipadoMes(rs.getDouble("limite_antecipacao_mes"));
                contaSalario.setPermiteAntecipar13o(rs.getBoolean("permite_antecipar_13o"));
                contaSalario.setIdContaVinculada(rs.getLong("id_conta_vinculada"));

				listaRetorno.add(contaSalario);
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

			throw new RuntimeException("Problemas ao consultar conta salario:\n" + e.getMessage());
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

	public List<ContaSalario> consultarTodos() {
		final String comando =	"""
                                SELECT cs.id_conta_bancaria, cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cs.cnpj_vinculado, cs.limite_consignado, cs.limite_antecipacao_mes, cs.permite_antecipar_13o, cs.id_conta_vinculada
				                FROM conta_salario cs
								INNER JOIN conta_bancaria cb
									ON cs.id_conta_bancaria = cb.id
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaSalario       contaSalario;
		List<ContaSalario> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaSalario = new ContaSalario();
                listaRetorno = new ArrayList<>();
				
				contaSalario.setId(rs.getLong("id_conta_bancaria"));
                contaSalario.setCodigoBanco(rs.getLong("codigo_banco"));
				contaSalario.setNumAgencia(rs.getLong("num_agencia"));
				contaSalario.setNumConta(rs.getLong("num_conta"));
				contaSalario.depositar(rs.getDouble("saldo"));
				contaSalario.setDataAbertura(rs.getDate("data_abertura"));
				contaSalario.setIdTitular(rs.getLong("id_titular"));
				contaSalario.setCnpjVinculado(rs.getString("cnpj_vinculado"));
                contaSalario.setLimiteConsignado(rs.getDouble("limite_consignado"));
                contaSalario.setLimiteAntecipadoMes(rs.getDouble("limite_antecipacao_mes"));
                contaSalario.setPermiteAntecipar13o(rs.getBoolean("permite_antecipar_13o"));
                contaSalario.setIdContaVinculada(rs.getLong("id_conta_vinculada"));

				listaRetorno.add(contaSalario);				
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

			throw new RuntimeException("Problemas ao consultar conta salario:\n" + e.getMessage());
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
