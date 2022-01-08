package com.ti2cc;

import java.io.Serializable;

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private String login;
	private String nome;
	private String email;
	private String senha;


	public Usuario() {
		login = "";
		nome = "";
		email = "";
		senha = "";
	}

	public Usuario(String login, String nome, String email, String senha) {
		setLogin(login);
		setNome(nome);
		setEmail(email);
		setSenha(senha);
	}		


	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Usuário: " + login + " Nome: " + nome + " E-mail: " + email + " Senha: " + senha;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getLogin() == ((Usuario) obj).getLogin());
	}

	public void add(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}	
}