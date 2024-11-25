package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class MySQL {
	private static final String BANCO   = "jdbc:mysql://localhost:3306/conta_bancaria";
	private static final String USUARIO = "gerente_banco";
	private static final String SENHA   = "banco123";

	private MySQL(){}
	
	public static Connection conectar() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(BANCO, USUARIO, SENHA);
		} catch (SQLException | ClassNotFoundException ex) {
			throw new RuntimeException("Problemas ao estabelecer conexão:\n" + ex.getMessage());
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
