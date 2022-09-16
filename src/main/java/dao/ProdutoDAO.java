package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO extends DAO {
	public ProdutoDAO() {
		super();
		conectar();
	}
	
	public boolean insert(Produto produto)  {
		try {
			PreparedStatement st = conexao.prepareStatement("INSERT INTO produto (descricao, preco, quantidade, datafabricacao, datavalidade) VALUES (?, ?, ?, ?, ?);");
			st.setString(1, produto.getDescricao());
			st.setDouble(2, produto.getPreco());
			st.setInt(3, produto.getQuantidade());
			st.setTimestamp(4, Timestamp.valueOf(produto.getDataFabricacao()));
			st.setDate(5, Date.valueOf(produto.getDataValidade()));
			return st.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public Produto get(int id) {
		Produto produto = null;
		
		try {
			PreparedStatement st = conexao.prepareStatement("SELECT * FROM produto WHERE id=?", ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
	        if(rs.next()){            
	        	 produto = new Produto(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	                				   rs.getInt("quantidade"), 
	        			               rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			               rs.getDate("datavalidade").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	
	public List<Produto> get() {
		return get("");
	}

	
	public List<Produto> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Produto> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Produto> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Produto> get(String orderBy) {
		List<Produto> produtos = new ArrayList<>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Produto p = new Produto(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	        			                rs.getInt("quantidade"),
	        			                rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			                rs.getDate("datavalidade").toLocalDate());
	            produtos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}
	
	
	public void update(Produto produto) {
		try {
			PreparedStatement st = conexao.prepareStatement("UPDATE produto SET descricao = ?, preco = ?, quantidade = ?, datafabricacao = ?, datavalidade = ? WHERE id = ?");
		    st.setString(1, produto.getDescricao());
			st.setDouble(2, produto.getPreco());
			st.setInt(3, produto.getQuantidade());
			st.setTimestamp(4, Timestamp.valueOf(produto.getDataFabricacao()));
			st.setDate(5, Date.valueOf(produto.getDataValidade()));
			st.setInt(6, produto.getID());
			st.executeUpdate();
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}
	
	
	public void delete(int id) {
		try {  
			PreparedStatement st = conexao.prepareStatement("DELETE FROM produto WHERE id = ?");
			st.setInt(1, id);
			st.executeUpdate();
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
	}
}