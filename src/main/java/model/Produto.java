package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Produto {


	private int id;
	private String descricao;
	private float preco;
	private int quantidade;
	private LocalDateTime dataFabricacao;	
	private LocalDate dataValidade;
	
	public Produto() {
		id = -1;
		descricao = "";
		preco = 0.00F;
		quantidade = 0;
		dataFabricacao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		dataValidade = LocalDate.now().plusMonths(6); // o default é uma validade de 6 meses.
	}

	public Produto(int id, String descricao, float preco, int quantidade, LocalDateTime fabricacao, LocalDate v) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setQuantidade(quantidade);
		setDataFabricacao(fabricacao);
		setDataValidade(v);
	}

	public Produto(String descricao, float preco, int quantidade, LocalDateTime fabricacao, LocalDate v) {
		this(-1, descricao, preco, quantidade, fabricacao, v);
	}

	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public LocalDate getDataValidade() {
		return dataValidade;
	}

	public LocalDateTime getDataFabricacao() {
		return dataFabricacao;
	}

	public void setDataFabricacao(LocalDateTime dataFabricacao) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (dataFabricacao.isBefore(agora))
			this.dataFabricacao = dataFabricacao;
	}

	public void setDataValidade(LocalDate dataValidade) {
		// a data de fabricação deve ser anterior é data de validade.
		if (getDataFabricacao().isBefore(dataValidade.atStartOfDay()))
			this.dataValidade = dataValidade;
	}

	public boolean emValidade() {
		return LocalDateTime.now().toLocalDate().isBefore(this.getDataValidade());
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Produto: " + descricao + "   Preço: R$" + preco + "   Quantidade.: " + quantidade + "   Fabricação: "
				+ dataFabricacao  + "   Data de Validade: " + dataValidade;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Produto produto = (Produto) o;
		return id == produto.id && Float.compare(produto.preco, preco) == 0 && quantidade == produto.quantidade && Objects.equals(descricao, produto.descricao) && Objects.equals(dataFabricacao, produto.dataFabricacao) && Objects.equals(dataValidade, produto.dataValidade);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, descricao, preco, quantidade, dataFabricacao, dataValidade);
	}
}