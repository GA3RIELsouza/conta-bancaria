package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL
{
	private final static String address  = "jdbc:mysql://localhost:3306/conta_bancaria";
	private final static String user     = "gerente_banco";
	private final static String password = "123";
	
	public static Connection conectar()
	{
		try
		{
			return DriverManager.getConnection(address, user, password);
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