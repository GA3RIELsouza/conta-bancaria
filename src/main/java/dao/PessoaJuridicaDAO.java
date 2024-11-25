package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.MySQL;
import enums.Situacao;
import interfaces.IPessoaJuridicaDAO;
import models.PessoaJuridica;

public class PessoaJuridicaDAO implements IPessoaJuridicaDAO {
    public void incluir(PessoaJuridica pessoaJuridica) {
		incluir(pessoaJuridica, false);
	}
	
	public long incluir(PessoaJuridica pessoaJuridica, boolean retornaId) {
		if (pessoaJuridica == null)
			return -1;
		
        final String comandoPessoa         =    """
                                                INSERT INTO pessoa (id_localidade, num_endereco, compl_endereco, situacao)
                                                VALUES (?, ?, ?, ?)
                                                """;
		final String comandoPessoaJuridica =    """
										        INSERT INTO pessoa_juridica (id_pessoa, cnpj, razao_social, nome_fantasia, inscr_estadual)
											    VALUES (?, ?, ?, ?, ?)
											    """;
		Connection        con = null;
		PreparedStatement pstmPessoa         = null;
		PreparedStatement pstmPessoaJuridica = null;
		long              idPessoa;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmPessoa = con.prepareStatement(comandoPessoa, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmPessoa.setLong(1, pessoaJuridica.getIdLocalidade());
			pstmPessoa.setInt(2, pessoaJuridica.getNumEndereco());
			pstmPessoa.setString(3, pessoaJuridica.getComplEndereco());
			pstmPessoa.setInt(4, pessoaJuridica.getSituacao().getId());

			pstmPessoa.execute();

			ResultSet rsPessoa = pstmPessoa.getGeneratedKeys();
			if (rsPessoa.next());
				idPessoa = rsPessoa.getLong(1);
			
			pstmPessoaJuridica = con.prepareStatement(comandoPessoaJuridica);

			pstmPessoaJuridica.setLong(1, idPessoa);
			pstmPessoaJuridica.setString(2, pessoaJuridica.getCnpj());
			pstmPessoaJuridica.setString(3, pessoaJuridica.getRazaoSocial());
			pstmPessoaJuridica.setString(4, pessoaJuridica.getNomeFantasia());
			pstmPessoaJuridica.setLong(5, pessoaJuridica.getInscrEstadual());
			
			pstmPessoaJuridica.execute();

			con.commit();

			if (!retornaId)
				idPessoa = -1L;

			return idPessoa;
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao incluir pessoa juridica:\n" + e.getMessage());
		} finally {
			try {
				if (pstmPessoa != null)
					pstmPessoa.close();

				if (pstmPessoaJuridica != null)
					pstmPessoaJuridica.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void atualizar(PessoaJuridica pessoaJuridica) {
		if (pessoaJuridica == null)
			return;
		
        final String comandoPessoa         =    """
                                                UPDATE pessoa
                                                SET id_localidade = ?, num_endereco = ?, compl_endereco = ?, situacao = ?
                                                WHERE id = ?
                                                """;
		final String comandoPessoaJuridica =    """
                                                UPDATE pessoa_juridica
                                                SET cnpj = ?, razao_social = ?, nome_fantasia = ?, inscr_estadual = ?
                                                WHERE id_pessoa = ?
                                                """;
		Connection        con = null;
		PreparedStatement pstmPessoa         = null;
		PreparedStatement pstmPessoaJuridica = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmPessoa = con.prepareStatement(comandoPessoa);

			pstmPessoa.setLong(1, pessoaJuridica.getIdLocalidade());
			pstmPessoa.setInt(2, pessoaJuridica.getNumEndereco());
			pstmPessoa.setString(3, pessoaJuridica.getComplEndereco());
			pstmPessoa.setInt(4, pessoaJuridica.getSituacao().getId());
			pstmPessoa.setLong(5, pessoaJuridica.getId());

			pstmPessoaJuridica = con.prepareStatement(comandoPessoaJuridica);
			
			pstmPessoaJuridica.setString(1, pessoaJuridica.getCnpj());
			pstmPessoaJuridica.setString(2, pessoaJuridica.getRazaoSocial());
			pstmPessoaJuridica.setString(3, pessoaJuridica.getNomeFantasia());
			pstmPessoaJuridica.setLong(4, pessoaJuridica.getInscrEstadual());
            pstmPessoaJuridica.setLong(5, pessoaJuridica.getId());
			
			pstmPessoaJuridica.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao atualizar pessoa juridica:\n" + e.getMessage());
		} finally {
			try {
				if (pstmPessoa != null)
					pstmPessoa.close();

				if (pstmPessoaJuridica != null)
					pstmPessoaJuridica.close();
				
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
		final String comandoPessoaJuridica =    """
                                                DELETE FROM pessoa_juridica
                                                WHERE id_pessoa = ?
                                                """;
		final String comandoPessoa         =    """
                                                DELETE FROM pessoa
                                                WHERE id = ?
                                                """;
		Connection        con = null;
		PreparedStatement pstmPessoaJuridica = null;
		PreparedStatement pstmPessoa         = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmPessoaJuridica = con.prepareStatement(comandoPessoaJuridica);
			pstmPessoaJuridica.setLong(1, id);
			pstmPessoaJuridica.execute();

			pstmPessoa = con.prepareStatement(comandoPessoa);
			pstmPessoa.setLong(1, id);
			pstmPessoa.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao atualizar pessoa juridica:\n" + e.getMessage());
		} finally {
			try {
				if (pstmPessoa != null)
					pstmPessoa.close();

				if (pstmPessoaJuridica != null)
					pstmPessoaJuridica.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
	
	public PessoaJuridica consultarPorId(long id) {
		final String comando =  """
                                SELECT p.id_localidade, p.num_endereco, p.compl_endereco, p.situacao,
								pf.cnpj, pf.razao_social, pf.nome_fantasia, pf.inscr_estadual
				                FROM pessoa_juridica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                			    WHERE pf.id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaJuridica    pessoaJuridica;

		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaJuridica = new PessoaJuridica();
				
				pessoaJuridica.setId(id);
				pessoaJuridica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaJuridica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaJuridica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaJuridica.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				pessoaJuridica.setCnpj(rs.getString("cnpj"));
				pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
				pessoaJuridica.setNomeFantasia(rs.getString("nome_fantasia"));
				pessoaJuridica.setInscrEstadual(rs.getLong("inscr_estadual"));
				
				return pessoaJuridica;
			} else {
				return null;
			}
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao consultar pessoa juridica:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public PessoaJuridica consultarPorSituacao(Situacao situacao) {
		final String comando =  """
                                SELECT pf.id_pessoa, p.id_localidade, p.num_endereco, p.compl_endereco,
								pf.cnpj, pf.razao_social, pf.nome_fantasia, pf.inscr_estadual
				                FROM pessoa_juridica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                			    WHERE p.situacao = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaJuridica    pessoaJuridica;

		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			pstm.setInt(1, situacao.getId());
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaJuridica = new PessoaJuridica();
				
				pessoaJuridica.setId(rs.getLong("id_pessoa"));
				pessoaJuridica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaJuridica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaJuridica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaJuridica.setSituacao(situacao);
				pessoaJuridica.setCnpj(rs.getString("cnpj"));
				pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
				pessoaJuridica.setNomeFantasia(rs.getString("nome_fantasia"));
				pessoaJuridica.setInscrEstadual(rs.getLong("inscr_estadual"));
				
				return pessoaJuridica;
			} else {
				return null;
			}
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao consultar pessoa juridica:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public PessoaJuridica consultarPorCnpj(String cnpj) {
		final String comando =  """
                                SELECT pf.id_pessoa, p.id_localidade, p.num_endereco, p.compl_endereco, p.situacao,
								pf.razao_social, pf.nome_fantasia, pf.inscr_estadual
				                FROM pessoa_juridica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                			    WHERE pf.cnpj = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaJuridica    pessoaJuridica;

		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, cnpj);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaJuridica = new PessoaJuridica();
				
				pessoaJuridica.setId(rs.getLong("id_pessoa"));
				pessoaJuridica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaJuridica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaJuridica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaJuridica.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				pessoaJuridica.setCnpj(cnpj);
				pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
				pessoaJuridica.setNomeFantasia(rs.getString("nome_fantasia"));
				pessoaJuridica.setInscrEstadual(rs.getLong("inscr_estadual"));
				
				return pessoaJuridica;
			} else {
				return null;
			}
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao consultar pessoa juridica:\n" + e.getMessage());
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public List<PessoaJuridica> consultarTodos() {
		final String comando =	"""
                                SELECT pf.id_pessoa, p.id_localidade, p.num_endereco, p.compl_endereco, p.situacao,
								pf.cnpj, pf.razao_social, pf.nome_fantasia, pf.inscr_estadual
				                FROM pessoa_juridica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                                """;
		Connection         con = null;
		PreparedStatement  pstm = null;
		ResultSet          rs   = null;
		PessoaJuridica       pessoaJuridica;
		List<PessoaJuridica> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				pessoaJuridica = new PessoaJuridica();
                listaRetorno = new ArrayList<>();
				
				pessoaJuridica.setId(rs.getLong("id_pessoa"));
				pessoaJuridica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaJuridica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaJuridica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaJuridica.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				pessoaJuridica.setCnpj(rs.getString("cnpj"));
				pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
				pessoaJuridica.setNomeFantasia(rs.getString("nome_fantasia"));
				pessoaJuridica.setInscrEstadual(rs.getLong("inscr_estadual"));

				listaRetorno.add(pessoaJuridica);				
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

			throw new RuntimeException("Problemas ao consultar pessoa juridica:\n" + e.getMessage());
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
