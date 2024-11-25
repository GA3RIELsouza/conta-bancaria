package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import enums.Sexo;
import enums.Situacao;
import models.PessoaFisica;
import interfaces.IPessoaFisicaDAO;

public class PessoaFisicaDAO implements IPessoaFisicaDAO {
    public void incluir(PessoaFisica pessoaFisica) {
		incluir(pessoaFisica, false);
	}
	
	public long incluir(PessoaFisica pessoaFisica, boolean retornaId) {
		if (pessoaFisica == null)
			return -1;
		
		final String comandoPessoa       =  """
											INSERT INTO pessoa (id_localidade, num_endereco, compl_endereco, situacao)
											VALUES (?, ?, ?, ?)
											""";
		final String comandoPessoaFisica =  """
											INSERT INTO pessoa_fisica (id_pessoa, cpf, nome, dt_nasc, sexo)
											VALUES (?, ?, ?, ?, ?)
											""";
		Connection        con = null;
		PreparedStatement pstmPessoa       = null;
		PreparedStatement pstmPessoaFisica = null;
		long              idPessoa;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmPessoa = con.prepareStatement(comandoPessoa, PreparedStatement.RETURN_GENERATED_KEYS);

			pstmPessoa.setLong(1, pessoaFisica.getIdLocalidade());
			pstmPessoa.setInt(2, pessoaFisica.getNumEndereco());
			pstmPessoa.setString(3, pessoaFisica.getComplEndereco());
			pstmPessoa.setInt(4, pessoaFisica.getSituacao().getId());

			pstmPessoa.execute();

			ResultSet rsPessoa = pstmPessoa.getGeneratedKeys();
			if (rsPessoa.next());
				idPessoa = rsPessoa.getLong(1);
			
			pstmPessoaFisica = con.prepareStatement(comandoPessoaFisica);

			pstmPessoaFisica.setLong(1, idPessoa);
			pstmPessoaFisica.setString(2, pessoaFisica.getCpf());
			pstmPessoaFisica.setString(3, pessoaFisica.getNome());
			pstmPessoaFisica.setDate(4, pessoaFisica.getDtNasc());
			pstmPessoaFisica.setInt(5, pessoaFisica.getSexo().getId());
			
			pstmPessoaFisica.execute();

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

			throw new RuntimeException("Problemas ao incluir pessoa fisica:\n" + e.getMessage());
		} finally {
			try {
				if (pstmPessoa != null)
					pstmPessoa.close();

				if (pstmPessoaFisica != null)
					pstmPessoaFisica.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}

	public void atualizar(PessoaFisica pessoaFisica) {
		if (pessoaFisica == null)
			return;
		
		final String comandoPessoa       =  """
											UPDATE pessoa
											SET id_localidade = ?, num_endereco = ?, compl_endereco = ?, situacao = ?
											WHERE id = ?
											""";
		final String comandoPessoaFisica =  """
											UPDATE pessoa_fisica
											SET cpf = ?, nome = ?, dt_nasc = ?, sexo = ?
											WHERE id_pessoa = ?
											""";
		Connection        con = null;
		PreparedStatement pstmPessoa       = null;
		PreparedStatement pstmPessoaFisica = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmPessoa = con.prepareStatement(comandoPessoa);

			pstmPessoa.setLong(1, pessoaFisica.getIdLocalidade());
			pstmPessoa.setInt(2, pessoaFisica.getNumEndereco());
			pstmPessoa.setString(3, pessoaFisica.getComplEndereco());
			pstmPessoa.setInt(4, pessoaFisica.getSituacao().getId());
			pstmPessoa.setLong(5, pessoaFisica.getId());

			pstmPessoaFisica = con.prepareStatement(comandoPessoaFisica);
			
			pstmPessoaFisica.setString(1, pessoaFisica.getCpf());
			pstmPessoaFisica.setString(2, pessoaFisica.getNome());
			pstmPessoaFisica.setDate(3, pessoaFisica.getDtNasc());
			pstmPessoaFisica.setInt(4, pessoaFisica.getSexo().getId());
            pstmPessoaFisica.setLong(5, pessoaFisica.getId());
			
			pstmPessoaFisica.execute();

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("Problemas ao realizar rollback:\n" + ex.getMessage());
				}
			}

			throw new RuntimeException("Problemas ao atualizar pessoa fisica:\n" + e.getMessage());
		} finally {
			try {
				if (pstmPessoa != null)
					pstmPessoa.close();

				if (pstmPessoaFisica != null)
					pstmPessoaFisica.close();
				
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
		final String comandoPessoaFisica =  """
											DELETE FROM pessoa_fisica
											WHERE id_pessoa = ?
											""";
		final String comandoPessoa       =  """
											DELETE FROM pessoa
											WHERE id = ?
											""";
		Connection        con = null;
		PreparedStatement pstmPessoaFisica = null;
		PreparedStatement pstmPessoa       = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstmPessoaFisica = con.prepareStatement(comandoPessoaFisica);
			pstmPessoaFisica.setLong(1, id);
			pstmPessoaFisica.execute();

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

			throw new RuntimeException("Problemas ao atualizar pessoa fisica:\n" + e.getMessage());
		} finally {
			try {
				if (pstmPessoa != null)
					pstmPessoa.close();

				if (pstmPessoaFisica != null)
					pstmPessoaFisica.close();
				
				if (con != null) {
					con.setAutoCommit(true);
					MySQL.desconectar(con);
				}
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao fechar recursos ou desconectar:\n" + ex.getMessage());
			}
        }
	}
	
	public PessoaFisica consultarPorId(long id) {
		final String comando =  """
                                SELECT p.id_localidade, p.num_endereco, p.compl_endereco, p.situacao,
								pf.cpf, pf.nome, pf.dt_nasc, pf.sexo
				                FROM pessoa_fisica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                			    WHERE pf.id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaFisica      pessoaFisica;

		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaFisica = new PessoaFisica();
				
				pessoaFisica.setId(id);
				pessoaFisica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaFisica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaFisica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaFisica.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				pessoaFisica.setCpf(rs.getString("cpf"));
				pessoaFisica.setNome(rs.getString("nome"));
				pessoaFisica.setDtNasc(rs.getDate("dt_nasc"));
				pessoaFisica.setSexo(Sexo.fromId(rs.getInt("sexo")));
				
				return pessoaFisica;
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

			throw new RuntimeException("Problemas ao consultar pessoa fisica:\n" + e.getMessage());
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

	public PessoaFisica consultarPorSituacao(Situacao situacao) {
		final String comando =  """
                                SELECT pf.id_pessoa, p.id_localidade, p.num_endereco, p.compl_endereco,
								pf.cpf, pf.nome, pf.dt_nasc, pf.sexo
				                FROM pessoa_fisica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                			    WHERE p.situacao = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaFisica      pessoaFisica;

		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			pstm.setInt(1, situacao.getId());
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaFisica = new PessoaFisica();
				
				pessoaFisica.setId(rs.getLong("id_pessoa"));
				pessoaFisica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaFisica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaFisica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaFisica.setSituacao(situacao);
				pessoaFisica.setCpf(rs.getString("cpf"));
				pessoaFisica.setNome(rs.getString("nome"));
				pessoaFisica.setDtNasc(rs.getDate("dt_nasc"));
				pessoaFisica.setSexo(Sexo.fromId(rs.getInt("sexo")));
				
				return pessoaFisica;
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

			throw new RuntimeException("Problemas ao consultar pessoa fisica:\n" + e.getMessage());
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

	public PessoaFisica consultarPorCpf(String cpf) {
		final String comando =  """
                                SELECT pf.id_pessoa, p.id_localidade, p.num_endereco, p.compl_endereco, p.situacao,
								pf.nome, pf.dt_nasc, pf.sexo
				                FROM pessoa_fisica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                			    WHERE pf.cpf = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaFisica      pessoaFisica;

		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, cpf);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaFisica = new PessoaFisica();
				
				pessoaFisica.setId(rs.getLong("id_pessoa"));
				pessoaFisica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaFisica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaFisica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaFisica.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				pessoaFisica.setCpf(cpf);
				pessoaFisica.setNome(rs.getString("nome"));
				pessoaFisica.setDtNasc(rs.getDate("dt_nasc"));
				pessoaFisica.setSexo(Sexo.fromId(rs.getInt("sexo")));
				
				return pessoaFisica;
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

			throw new RuntimeException("Problemas ao consultar pessoa fisica:\n" + e.getMessage());
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

	public List<PessoaFisica> consultarTodos() {
		final String comando =	"""
                                SELECT pf.id_pessoa, p.id_localidade, p.num_endereco, p.compl_endereco, p.situacao,
								pf.cpf, pf.nome, pf.dt_nasc, pf.sexo
				                FROM pessoa_fisica pf
								INNER JOIN pessoa p
									ON pf.id_pessoa = p.id
                                """;
		Connection         con = null;
		PreparedStatement  pstm = null;
		ResultSet          rs   = null;
		PessoaFisica       pessoaFisica;
		List<PessoaFisica> listaRetorno = null;
		
		try {
			con = MySQL.conectar();
			con.setAutoCommit(false);

			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				pessoaFisica = new PessoaFisica();
                listaRetorno = new ArrayList<>();
				
				pessoaFisica.setId(rs.getLong("id_pessoa"));
				pessoaFisica.setIdLocalidade(rs.getLong("id_localidade"));
                pessoaFisica.setNumEndereco(rs.getInt("num_endereco"));
                pessoaFisica.setComplEndereco(rs.getString("compl_endereco"));
                pessoaFisica.setSituacao(Situacao.fromId(rs.getInt("situacao")));
				pessoaFisica.setCpf(rs.getString("cpf"));
				pessoaFisica.setNome(rs.getString("nome"));
				pessoaFisica.setDtNasc(rs.getDate("dt_nasc"));
				pessoaFisica.setSexo(Sexo.fromId(rs.getInt("sexo")));

				listaRetorno.add(pessoaFisica);				
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

			throw new RuntimeException("Problemas ao consultar pessoa fisica:\n" + e.getMessage());
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
