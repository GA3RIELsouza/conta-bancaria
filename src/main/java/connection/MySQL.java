package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySQL {
	private static final String ADDRESS  = "jdbc:mysql://localhost:3306/conta_bancaria";
	private static final String USER     = "gerente_banco";
	private static final String PASSWORD = "banco123";

	private MySQL(){}
	
	public static Connection conectar() {
		try {
			return DriverManager.getConnection(ADDRESS, USER, PASSWORD);
		} catch (SQLException ex) {
			throw new RuntimeException("Problemas ao estabelecer a conexão:\n" + ex.getMessage());
		}
	}
	
	public static void desconectar(Connection conexao) {
		if (conexao != null) {
			try {
				conexao.close();
			} catch (SQLException ex) {
				throw new RuntimeException("Problemas ao encerrar a conexão:\n" + ex.getMessage());
			}
		}
	}
}