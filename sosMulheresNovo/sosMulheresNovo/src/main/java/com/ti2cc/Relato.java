package com.ti2cc;

import java.io.Serializable;
import java.util.Date;

public class Relato implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String nome;
	private Date data;
	private int idade;
	private String testemunho;	
	private String categoria;
	private String idUsuario;
	
	public Relato() {
		id = "";
		nome = "";
		data = null;
		idade = 0;
		testemunho = "";
		categoria = "";
	}

	public Relato(String id, String nome, Date data, int idade, String testemunho, String categoria) {
		setId(id);
		setNome(nome);
		setData(data);
		setIdade(idade);
		setTestemunho(testemunho);
		setCategoria(categoria);
	}		
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if (nome.length() >= 3)
			this.nome = nome;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}

	public int getIdade() {
		return idade;
	}
	public void setIdade(int idade) {
		if (idade > 0)
			this.idade = idade;
	}
	
	public String getTestemunho() {
		return testemunho;
	}
	public void setTestemunho(String testemunho) {
		this.testemunho = testemunho;
	}

	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Relato: " + nome + "   Data ocorrido: " + data + "  Idade: " + idade + "  Testemunho: "
				+ testemunho  + "   Categoria: " + categoria;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Relato) obj).getId());
	}	
}