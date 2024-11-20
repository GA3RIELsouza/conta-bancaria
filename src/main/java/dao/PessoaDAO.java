package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.MySQL;
import interfaces.IPessoaDAO;
import enums.Situacao;
import models.Pessoa;
import models.PessoaFisica;
import models.PessoaJuridica;

public class PessoaDAO implements IPessoaDAO {
    public void incluir(Pessoa pessoa) {
		incluir(pessoa, false);
	}
	
	public long incluir(Pessoa pessoa, boolean retornaId) {
		if (pessoa == null)
			return -1;
		
		final String comando =	"""
                                INSERT INTO pessoa (id_localidade, num_endereco, compl_endereco, situacao)
                                VALUES (?, ?, ?, ?)
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
			
			pstm.setLong(1, pessoa.getIdLocalidade());
			pstm.setInt(2, pessoa.getNumEndereco());
			pstm.setString(3, pessoa.getComplEndereco());
			pstm.setInt(4, pessoa.getSituacao().getId());
			
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
			throw new RuntimeException("Problemas ao incluir pessoa:\n" + e.getMessage());
		} finally {
            MySQL.desconectar(con);
        }
	}

	public void atualizar(Pessoa pessoa) {
		if (pessoa == null)
			return;
		
		final String comando =	"""
                                UPDATE pessoa
			                    SET id_localidade = ?, num_endereco = ?, compl_endereco = ?, situacao = ?
		                        WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, pessoa.getIdLocalidade());
			pstm.setInt(2, pessoa.getNumEndereco());
			pstm.setString(3, pessoa.getComplEndereco());
			pstm.setInt(4, pessoa.getSituacao().getId());
			pstm.setLong(5, pessoa.getId());
			
			pstm.execute();
			
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao atualizar pessoa:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}

	public void excluir(long id) {
		final String comando =	"""
                                DELETE FROM pessoa
		                        WHERE id = ?
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
			throw new RuntimeException("Problemas ao excluir pessoa:\n" + e.getMessage());
		} finally {
			MySQL.desconectar(con);
		}
	}
	
	public Pessoa consultarPorId(Pessoa pessoa) {
		final String comando =	"""
                                SELECT id_localidade, num_endereco, compl_endereco, situacao
				                FROM pessoa
                			    WHERE id = ?
                            	""";
		Connection        con = null;
		PreparedStatement pstm = null;
		ResultSet         rs   = null;
		
		try {
			con = MySQL.conectar();
			pstm = con.prepareStatement(comando);
			
			pstm.setLong(1, pessoa.getId());
			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				pessoa.setIdLocalidade(rs.getLong("id_localidade"));
				pessoa.setNumEndereco(rs.getInt("num_endereco"));
				pessoa.setComplEndereco(rs.getString("compl_endereco"));
				pessoa.setSituacao(Situacao.fromId(rs.getInt("situacao")));

				pstm.close();
				MySQL.desconectar(con);
				
				return pessoa;
			} else {
				pstm.close();
				MySQL.desconectar(con);

				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problemas ao consultar pessoa:\n" + e.getMessage());
		}
	}

	public List<Pessoa> consultarTodos() {
		List<Pessoa>         listaRetorno      = new ArrayList<>();
		PessoaFisicaDAO      pessoaFisicaDAO   = new PessoaFisicaDAO();
		PessoaJuridicaDAO    pessoaJuridicaDAO = new PessoaJuridicaDAO();
		List<PessoaFisica>   pessoasFisicas    = pessoaFisicaDAO.consultarTodos();
		List<PessoaJuridica> pessoasJuridicas  = pessoaJuridicaDAO.consultarTodos();
		
		if (pessoasFisicas != null)
			listaRetorno.addAll(pessoasFisicas);
		if (pessoasJuridicas != null)
			listaRetorno.addAll(pessoasJuridicas);
		
		listaRetorno.sort((p1, p2) -> Long.compare(p1.getId(), p2.getId()));
		
		return listaRetorno;
	}
}
