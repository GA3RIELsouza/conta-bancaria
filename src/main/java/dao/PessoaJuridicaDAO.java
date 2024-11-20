package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import models.Pessoa;
import models.PessoaJuridica;
import interfaces.IPessoaJuridicaDAO;

public class PessoaJuridicaDAO implements IPessoaJuridicaDAO {
    public void incluir(PessoaJuridica pessoaJuridica) {
		incluir(pessoaJuridica, false);
	}
	
	public long incluir(PessoaJuridica pessoaJuridica, boolean retornaId) {
		if (pessoaJuridica == null)
			return -1;
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaJuridica.setId(pessoaDAO.incluir(pessoaJuridica, true));
		
		final String comando =  """
                                INSERT INTO pessoa_juridica (id_pessoa, cnpj, razao_social, nome_fantasia, inscr_estadual)
                                VALUES (?, ?, ?, ?, ?)
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, pessoaJuridica.getId());
			pstm.setString(2, pessoaJuridica.getCnpj());
			pstm.setString(3, pessoaJuridica.getRazaoSocial());
			pstm.setString(4, pessoaJuridica.getNomeFantasia());
			pstm.setString(5, pessoaJuridica.getInscrEstadual());
			
			pstm.execute();
						
			pstm.close();

			return pessoaJuridica.getId();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir pessoa juridica:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(PessoaJuridica pessoaJuridica) {
		if (pessoaJuridica == null)
			return;
		
		final String comando =  """
                                UPDATE pessoa_juridica
			                    SET cnpj = ?, razao_social = ?, nome_fantasia = ?, inscr_estadual = ?
		                        WHERE id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, pessoaJuridica.getCnpj());
			pstm.setString(2, pessoaJuridica.getRazaoSocial());
			pstm.setString(3, pessoaJuridica.getNomeFantasia());
			pstm.setString(4, pessoaJuridica.getInscrEstadual());
            pstm.setLong(5, pessoaJuridica.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar pessoa juridica:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =  """
                                DELETE FROM pessoa_juridica
		                        WHERE id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);

			pstm.execute();
			
			pstm.close();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(id);
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao excluir pessoa juridica:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public PessoaJuridica consultarPorId(long id) {
		final String comando =  """
                                SELECT cnpj, razao_social, nome_fantasia, inscr_estadual
				                FROM pessoa_juridica
                			    WHERE id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaJuridica      pessoaJuridica;
        PessoaDAO         pessoaDAO;
        Pessoa            pessoa;

		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaJuridica = new PessoaJuridica();
				
				pessoaJuridica.setId(id);
				pessoaJuridica.setCnpj(rs.getString("cnpj"));
				pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
				pessoaJuridica.setNomeFantasia(rs.getString("nome_fantasia"));
				pessoaJuridica.setInscrEstadual(rs.getString("inscr_estadual"));
                
                pessoaDAO = new PessoaDAO();
                pessoa = pessoaDAO.consultarPorId(pessoaJuridica);

                pessoaJuridica.setIdLocalidade(pessoa.getIdLocalidade());
                pessoaJuridica.setNumEndereco(pessoa.getNumEndereco());
                pessoaJuridica.setComplEndereco(pessoa.getComplEndereco());
                pessoa.setSituacao(pessoa.getSituacao());

				pstm.close();
				MySQL.desconectar(con);
				
				return pessoaJuridica;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar pessoa juridica:\n" + e.getMessage());
		}
	}

	public List<PessoaJuridica> consultarTodos() {
		final String comando =  """
                                SELECT id_pessoa, cnpj, razao_social, nome_fantasia, inscr_estadual
	                            FROM pessoa_juridica
                                """;
		Connection           con = null;
		PreparedStatement    pstm = null;
		ResultSet            rs   = null;
		PessoaJuridica       pessoaJuridica;
		List<PessoaJuridica> listaRetorno = null;
        PessoaDAO            pessoaDAO;
        Pessoa               pessoa;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				pessoaJuridica = new PessoaJuridica();
                listaRetorno = new ArrayList<>();
				
				pessoaJuridica.setId(rs.getLong("id_pessoa"));
				pessoaJuridica.setCnpj(rs.getString("cnpj"));
				pessoaJuridica.setRazaoSocial(rs.getString("razao_social"));
				pessoaJuridica.setNomeFantasia(rs.getString("nome_fantasia"));
				pessoaJuridica.setInscrEstadual(rs.getString("inscr_estadual"));

                pessoaDAO = new PessoaDAO();
                pessoa = pessoaDAO.consultarPorId(pessoaJuridica);

                pessoaJuridica.setIdLocalidade(pessoa.getIdLocalidade());
                pessoaJuridica.setNumEndereco(pessoa.getNumEndereco());
                pessoaJuridica.setComplEndereco(pessoa.getComplEndereco());
                pessoa.setSituacao(pessoa.getSituacao());

				listaRetorno.add(pessoaJuridica);				
			}

			pstm.close();
			MySQL.desconectar(con);

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar pessoa juridica:\n" + e.getMessage());
		}		
	}
}
