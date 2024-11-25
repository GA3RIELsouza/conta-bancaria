package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.IContaEspecialDAO;
import models.ContaEspecial;

public class ContaEspecialDAO implements IContaEspecialDAO {
    public void promoverConta(long id, double limiteCredito, Date dataVctoCntrato) {
        final String comando =	"""
								INSERT INTO conta_especial (id_conta_corrente, limite_credito, data_vcto_contrato)
								VALUES (?, ?, ?)
								""";
		Connection con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			
			pstm = con.prepareStatement(comando, PreparedStatement.RETURN_GENERATED_KEYS);
			
			pstm.setLong(1, id);
            pstm.setDouble(2, limiteCredito);
            pstm.setDate(3, dataVctoCntrato);
				
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao promover conta especial:\n" + e.getMessage());
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

    public void incluir(ContaEspecial contaEspecial) {
		incluir(contaEspecial, false);
	}
	
	public long incluir(ContaEspecial contaEspecial, boolean retornaId) {
		if (contaEspecial == null)
			return -1;
		
        ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO();
        long idContaCorrente = contaCorrenteDAO.incluir(contaEspecial, true);

        contaEspecial.setId(idContaCorrente);
        promoverConta(idContaCorrente, contaEspecial.getLimiteCredito(), contaEspecial.getDataVctoContrato());

        return idContaCorrente;
        
	}

	public void atualizar(ContaEspecial contaEspecial) {
		if (contaEspecial == null)
			return;
		
		final String comando =	"""
								UPDATE conta_especial
								SET limite_credito = ?, data_vcto_contrato = ?
								WHERE id_conta_corrente = ?
								""";
		Connection con = null;
		PreparedStatement pstm = null;
        ContaCorrenteDAO contaCorrenteDAO = null;
		
		try {
            contaCorrenteDAO = new ContaCorrenteDAO();
            contaCorrenteDAO.atualizar(contaEspecial);

			con = MySQL.conectar();

			pstm = con.prepareStatement(comando);

            pstm.setDouble(1, contaEspecial.getLimiteCredito());
            pstm.setDate(2, contaEspecial.getDataVctoContrato());
            pstm.setLong(3, contaEspecial.getId());
			
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar conta especial:\n" + e.getMessage());
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

    public void rebaixarConta(long id) {
        final String comando =	"""
								DELETE FROM conta_especial
								WHERE id_conta_corrente = ?
								""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
            con = MySQL.conectar();

			pstm = con.prepareStatement(comando);

            pstm.setLong(1, id);

			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao rebaixar conta especial:\n" + e.getMessage());
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
        rebaixarConta(id);
        
        ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO();
        contaCorrenteDAO.excluir(id);
	}
	
	public ContaEspecial consultarPorId(long id) {
		final String comando =	"""
                                SELECT ce.limite_credito, ce.data_vcto_contrato,
                                cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_especial ce
                                INNER JOIN conta_corrente cc
                                    ON ce.id_conta_corrente = cc.id_conta_bancaria
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                			    WHERE ce.id_conta_corrente = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
        ContaEspecial     contaEspecial = null;
		
		try {
            con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
                contaEspecial = new ContaEspecial();

				contaEspecial.setId(id);
                contaEspecial.setCodigoBanco(rs.getLong("codigo_banco"));
				contaEspecial.setNumAgencia(rs.getLong("num_agencia"));
				contaEspecial.setNumConta(rs.getLong("num_conta"));
				contaEspecial.depositar(rs.getDouble("saldo"));
				contaEspecial.setDataAbertura(rs.getDate("data_abertura"));
				contaEspecial.setIdTitular(rs.getLong("id_titular"));
				contaEspecial.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaEspecial.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));
                contaEspecial.setLimiteCredito(rs.getDouble("limite_credito"));
                contaEspecial.setDataVctoContrato(rs.getDate("data_vcto_contrato"));

				return contaEspecial;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta especial:\n" + e.getMessage());
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

	public List<ContaEspecial> consultarPorTitular(long idTitular) {
		final String comando =	"""
                                SELECT ce.id_conta_corrente, ce.limite_credito, ce.data_vcto_contrato,
                                cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_especial ce
                                INNER JOIN conta_corrente cc
                                    ON ce.id_conta_corrente = cc.id_conta_bancaria
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                			    WHERE cb.id_titular = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaEspecial       contaEspecial;
		List<ContaEspecial> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, idTitular);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaEspecial = new ContaEspecial();
                listaRetorno = new ArrayList<>();
				
				contaEspecial.setId(rs.getLong("id_conta_corrente"));
                contaEspecial.setCodigoBanco(rs.getLong("codigo_banco"));
				contaEspecial.setNumAgencia(rs.getLong("num_agencia"));
				contaEspecial.setNumConta(rs.getLong("num_conta"));
				contaEspecial.depositar(rs.getDouble("saldo"));
				contaEspecial.setDataAbertura(rs.getDate("data_abertura"));
				contaEspecial.setIdTitular(idTitular);
                contaEspecial.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaEspecial.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));
				contaEspecial.setLimiteCredito(rs.getDouble("limite_credito"));
                contaEspecial.setDataVctoContrato(rs.getDate("data_vcto_contrato"));

				listaRetorno.add(contaEspecial);
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta especial:\n" + e.getMessage());
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

    public List<ContaEspecial> consultarPorBanco(long codigoBanco) {
		final String comando =	"""
                                SELECT ce.id_conta_corrente, ce.limite_credito, ce.data_vcto_contrato,
                                cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_especial ce
                                INNER JOIN conta_corrente cc
                                    ON ce.id_conta_corrente = cc.id_conta_bancaria
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                			    WHERE cb.codigo_banco = ?
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaEspecial       contaEspecial;
		List<ContaEspecial> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);

			pstm.setLong(1, codigoBanco);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaEspecial = new ContaEspecial();
                listaRetorno = new ArrayList<>();
				
				contaEspecial.setId(rs.getLong("id_conta_bancaria"));
                contaEspecial.setCodigoBanco(codigoBanco);
				contaEspecial.setNumAgencia(rs.getLong("num_agencia"));
				contaEspecial.setNumConta(rs.getLong("num_conta"));
				contaEspecial.depositar(rs.getDouble("saldo"));
				contaEspecial.setDataAbertura(rs.getDate("data_abertura"));
				contaEspecial.setIdTitular(rs.getLong("id_titular"));
				contaEspecial.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaEspecial.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));
                contaEspecial.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaEspecial.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));
                contaEspecial.setLimiteCredito(rs.getDouble("limite_credito"));
                contaEspecial.setDataVctoContrato(rs.getDate("data_vcto_contrato"));

				listaRetorno.add(contaEspecial);
			}

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta especial:\n" + e.getMessage());
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

	public List<ContaEspecial> consultarTodos() {
		final String comando =	"""
                                SELECT ce.id_conta_corrente, ce.limite_credito, ce.data_vcto_contrato,
                                cb.codigo_banco, cb.num_agencia, cb.num_conta, cb.saldo, cb.data_abertura, cb.id_titular,
								cc.valor_cesta_servicos, cc.limite_pix_noturno
				                FROM conta_especial ce
                                INNER JOIN conta_corrente cc
                                    ON ce.id_conta_corrente = cc.id_conta_bancaria
								INNER JOIN conta_bancaria cb
									ON cc.id_conta_bancaria = cb.id
                                """;
		Connection          con = null;
		PreparedStatement   pstm = null;
		ResultSet           rs   = null;
		ContaEspecial       contaEspecial;
		List<ContaEspecial> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				contaEspecial = new ContaEspecial();
                listaRetorno = new ArrayList<>();
				
				contaEspecial.setId(rs.getLong("id_conta_corrente"));
                contaEspecial.setCodigoBanco(rs.getLong("codigo_banco"));
				contaEspecial.setNumAgencia(rs.getLong("num_agencia"));
				contaEspecial.setNumConta(rs.getLong("num_conta"));
				contaEspecial.depositar(rs.getDouble("saldo"));
				contaEspecial.setDataAbertura(rs.getDate("data_abertura"));
				contaEspecial.setIdTitular(rs.getLong("id_titular"));
				contaEspecial.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaEspecial.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));
                contaEspecial.setLimiteCredito(rs.getDouble("limite_credito"));
                contaEspecial.setDataVctoContrato(rs.getDate("data_vcto_contrato"));
                

				listaRetorno.add(contaEspecial);				
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

			throw new RuntimeException("Problemas ao consultar conta especial:\n" + e.getMessage());
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
