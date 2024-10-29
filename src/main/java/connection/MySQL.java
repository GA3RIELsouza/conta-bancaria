package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class MySQL
{
	private static final String ADDRESS  = "jdbc:mysql://localhost:3306/conta_bancaria";
	private static final String USER     = "gerente_banco";
	private static final String PASSWORD = "banco123";
	
	public static Connection conectar()
	{
		try
		{
			return DriverManager.getConnection(ADDRESS, USER, PASSWORD);
		}
		catch(SQLException ex)
		{
			throw new RuntimeException("Erro ao tentar abrir uma conexão do banco de dados:\n" + ex.getMessage());
		}
	}
	
	public static void desconectar(Connection conexao)
	{
		if(conexao != null)
		{
			try
			{
				conexao.close();
			}
			catch(SQLException ex)
			{
				throw new RuntimeException("Erro ao tentar fechar uma conexão do banco de dados:\n" + ex.getMessage());
			}
		}
	}
}