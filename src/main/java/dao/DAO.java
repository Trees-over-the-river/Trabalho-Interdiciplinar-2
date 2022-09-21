package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO implements AutoCloseable {
	protected Connection conexao;
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
		System.out.println("Conexão efetuada com o postgres em " + url + " na tabela "+ mydatabase + "!");

		return status;
	}


	@Override
	public void close() throws SQLException {
		conexao.close();
	}
}