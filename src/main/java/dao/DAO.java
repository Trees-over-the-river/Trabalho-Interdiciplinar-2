package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DAO implements AutoCloseable {

	private Connection conexao;
	public final String serverName;
	public final String mydatabase;

	public final String username;
	public final String password;


	public DAO(String serverName, String mydatabase, String username, String password) {
		this.serverName = serverName;
		this.mydatabase = mydatabase;
		this.username = username;
		this.password = password;
	}

	public boolean conectar() throws SQLException {
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		boolean status = false;

		conexao = DriverManager.getConnection(url, username, password);
		status = (conexao != null);
		Logger.getLogger("Logger").fine("Conexão efetuada com o postgres em " + url + " na tabela "+ mydatabase + "!");

		return status;
	}

	public Connection getConexao() throws SQLException {

		if (conexao == null) conectar();
		return conexao;
	}

	@Override
	public void close() throws SQLException {
		conexao.close();
	}
}