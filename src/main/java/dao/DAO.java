package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

		System.out.println("[DAO] Conectando ao banco de dados Postgres em \"" + url + "\"");
		conexao = DriverManager.getConnection(url, username, password);
		status = (conexao != null);
		System.out.println("[DAO] Conexão efetuada");

		return status;
	}

	public Connection getConexao() throws SQLException {
		System.out.println("[DAO]Adiquirindo conexão com banco de dados");
		if (conexao == null) conectar();
		return conexao;
	}

	@Override
	public void close() throws SQLException {
		conexao.close();
	}
}