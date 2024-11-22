package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import enums.Estado;
import interfaces.IContaCorrenteDAO;
import models.ContaBancaria;
import models.ContaCorrente;
import models.Localidade;

public class ContaCorrenteDAO implements IContaCorrenteDAO {
    public void incluir(ContaCorrente contaCorrente) {
		incluir(contaCorrente, false);
	}
	
	public long incluir(ContaCorrente contaCorrente, boolean retornaId) {
		if (contaCorrente == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO conta_corrente (valor_cesta_servicos, limite_pix_noturno)
                                VALUES (?, ?)
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
			
			pstm.setDouble(1, contaCorrente.getValorCestaServicos());
			pstm.setDouble(2, contaCorrente.getLimitePixNoturno());
			
			pstm.execute();
			
			if (retornaId) {
				ResultSet rs = pstm.getGeneratedKeys();
				rs.next();
				id = rs.getLong(1);
			}
			else
				id = (long) -1;
						
			pstm.close();

			return id;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir conta corrente:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(ContaCorrente contaCorrente) {
		if (contaCorrente == null)
			return;
		
		final String comando =	"""
                                UPDATE conta_corrente
			                    SET valor_cesta_servicos = ?, limite_pix_noturno = ?
		                        WHERE id_conta_bancaria = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setDouble(1, contaCorrente.getValorCestaServicos());
			pstm.setDouble(2, contaCorrente.getLimitePixNoturno());
			pstm.setLong(3, contaCorrente.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar conta corrente:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =	"""
                                DELETE FROM conta_corrente
		                        WHERE id_conta_bancaria = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);

			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao excluir conta_corrente:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public ContaCorrente consultarPorId(long id) {
		final String comando =	"""
                                SELECT valor_cesta_servicos, limite_pix_noturno
				                FROM conta_corrente
                			    WHERE id_conta_bancaria = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
        ContaCorrente     contaCorrente    = null;
        ContaBancariaDAO  contaBancariaDAO = null;
        ContaBancaria     contaBancaria    = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
                contaCorrente = new ContaCorrente();

				contaCorrente.setValorCestaServicos(rs.getDouble("valor_cesta_servicos"));
                contaCorrente.setLimitePixNoturno(rs.getDouble("limite_pix_noturno"));

                contaBancariaDAO = new ContaBancariaDAO();
                contaBancaria = contaBancariaDAO.consultarPorId(contaCorrente);

                contaCorrente.setCodigoBanco(contaBancaria.getCodigoBanco());
				contaCorrente.setNumAgencia(contaBancaria.getNumAgencia());
				contaCorrente.depositar(contaBancaria.getSaldo());
				contaCorrente.setDataAbertura(contaBancaria.getDataAbertura());
				contaCorrente.setIdTitular(contaBancaria.getIdTitular());

				pstm.close();
				MySQL.desconectar(con);
				
				return contaCorrente;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar conta corrente:\n" + e.getMessage());
		}
	}

	public List<ContaCorrente> consultarTodosPorTitular(long idTitular) {
		final String comando =	"""
                                SELECT id, cep, estado, cidade, bairro, logradouro
	                            FROM conta_corrente
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

			pstm.close();
			MySQL.desconectar(con);

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar localidade:\n" + e.getMessage());
		}		
	}

    public List<ContaCorrente> consultarTodosPorBanco(ContaCorrente contaCorrente) {
		return null;
	}

	public List<ContaCorrente> consultarTodos() {
		return null;
	}
}
