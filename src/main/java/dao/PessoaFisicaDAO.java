package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import enums.Sexo;
import models.Pessoa;
import models.PessoaFisica;
import interfaces.IPessoaFisicaDAO;

public class PessoaFisicaDAO implements IPessoaFisicaDAO {
    public void incluir(PessoaFisica pessoaFisica) {
		incluir(pessoaFisica, false);
	}
	
	public long incluir(PessoaFisica pessoaFisica, boolean retornaId) {
		if (pessoaFisica == null)
			return -1;
        
        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaFisica.setId(pessoaDAO.incluir(pessoaFisica, true));
		
		final String comando =  """
                                INSERT INTO pessoa_fisica (id_pessoa, cpf, nome, dt_nasc, sexo)
                                VALUES (?, ?, ?, ?, ?)
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, pessoaFisica.getId());
			pstm.setString(2, pessoaFisica.getCpf());
			pstm.setString(3, pessoaFisica.getNome());
			pstm.setDate(4, pessoaFisica.getDtNasc());
			pstm.setInt(5, pessoaFisica.getSexo().getId());
			
			pstm.execute();

			pstm.close();

			return pessoaFisica.getId();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao incluir pessoa fisica:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(PessoaFisica pessoaFisica) {
		if (pessoaFisica == null)
			return;
		
		final String comando =  """
                                UPDATE pessoa_fisica
			                    SET cpf = ?, nome = ?, dt_nasc = ?, sexo = ?
		                        WHERE id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setString(1, pessoaFisica.getCpf());
			pstm.setString(2, pessoaFisica.getNome());
			pstm.setDate(3, pessoaFisica.getDtNasc());
			pstm.setInt(4, pessoaFisica.getSexo().getId());
            pstm.setLong(5, pessoaFisica.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar pessoa fisica:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =  """
                                DELETE FROM pessoa_fisica
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
			throw new RuntimeException("Problemas ao excluir pessoa fisica:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public PessoaFisica consultarPorId(long id) {
		final String comando =  """
                                SELECT cpf, nome, dt_nasc, sexo
				                FROM pessoa_fisica
                			    WHERE id_pessoa = ?
                                """;
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		PessoaFisica      pessoaFisica;
        PessoaDAO         pessoaDAO;
        Pessoa            pessoa;

		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, id);
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoaFisica = new PessoaFisica();
				
				pessoaFisica.setId(id);
				pessoaFisica.setCpf(rs.getString("cpf"));
				pessoaFisica.setNome(rs.getString("nome"));
				pessoaFisica.setDtNasc(rs.getDate("dt_nasc"));
				pessoaFisica.setSexo(Sexo.fromId(rs.getInt("sexo")));
                
                pessoaDAO = new PessoaDAO();
                pessoa = pessoaDAO.consultarPorId(pessoaFisica);

                pessoaFisica.setIdLocalidade(pessoa.getIdLocalidade());
                pessoaFisica.setNumEndereco(pessoa.getNumEndereco());
                pessoaFisica.setComplEndereco(pessoa.getComplEndereco());
                pessoa.setSituacao(pessoa.getSituacao());

				pstm.close();
				MySQL.desconectar(con);
				
				return pessoaFisica;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar pessoa fisica:\n" + e.getMessage());
		}
	}

	public List<PessoaFisica> consultarTodos() {
		final String comando =  """
                                SELECT id_pessoa, cpf, nome, dt_nasc, sexo
	                            FROM pessoa_fisica
                                """;
		Connection         con = null;
		PreparedStatement  pstm = null;
		ResultSet          rs   = null;
		PessoaFisica       pessoaFisica;
		List<PessoaFisica> listaRetorno = null;
        PessoaDAO          pessoaDAO;
        Pessoa             pessoa;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				pessoaFisica = new PessoaFisica();
                listaRetorno = new ArrayList<>();
				
				pessoaFisica.setId(rs.getLong("id_pessoa"));
				pessoaFisica.setCpf(rs.getString("cpf"));
				pessoaFisica.setNome(rs.getString("nome"));
				pessoaFisica.setDtNasc(rs.getDate("dt_nasc"));
				pessoaFisica.setSexo(Sexo.fromId(rs.getInt("sexo")));

                pessoaDAO = new PessoaDAO();
                pessoa = pessoaDAO.consultarPorId(pessoaFisica);

                pessoaFisica.setIdLocalidade(pessoa.getIdLocalidade());
                pessoaFisica.setNumEndereco(pessoa.getNumEndereco());
                pessoaFisica.setComplEndereco(pessoa.getComplEndereco());
                pessoa.setSituacao(pessoa.getSituacao());

				listaRetorno.add(pessoaFisica);				
			}

			pstm.close();
			MySQL.desconectar(con);

			return listaRetorno;
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar pessoa fisica:\n" + e.getMessage());
		}		
	}
}
