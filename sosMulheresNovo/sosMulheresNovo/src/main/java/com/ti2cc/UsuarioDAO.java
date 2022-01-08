package com.ti2cc;

import java.sql.*;

public class UsuarioDAO {
		private Connection conexao;
		
		/*public UsuarioDAO() {
			conexao = null;
		}*/
		
		public boolean conectar() {
			String driverName = "org.postgresql.Driver";
	        String serverName = "localhost";
	        String mydatabase = "sosmulheres";
	        int porta = 5433;
	        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
			String username = "ti2cc";
			String password = "ti@cc";
			boolean status = false;

			try {
				Class.forName(driverName);
				conexao = DriverManager.getConnection(url, username, password);
				status = (conexao == null);
				System.out.println("Conexão efetuada com o postgres!");
			} catch (ClassNotFoundException e) { 
				System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
			} catch (SQLException e) {
				System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
			}

			return status;
		}
		
		public boolean close() {
			boolean status = false;
			
			try {
				conexao.close();
				status = true;
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			return status;
		}

	public boolean add(Usuario usuario) {
		System.out.println("ok");
		boolean status = false;
		
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO usuario (login, nome, email, senha) "
					       + "VALUES (" +usuario.getLogin()+ ", '" + usuario.getNome() + "', '"  
					       + usuario.getEmail() + "', '" + usuario.getSenha() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	

	public boolean update(Usuario usuario) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE usuario SET nome = '" + usuario.getNome() + "', email = '"  
				       + usuario.getEmail() + "', senha = '" + usuario.getSenha() + "'"
					   + " WHERE login = " + usuario.getLogin();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean remove(String login) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM usuario WHERE login = " + login);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Usuario get(String login) {
		Usuario usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE login = " + login);		
	         //if(rs.next()){
	             //rs.last();
	             usuarios = new Usuario();
	             
	             //rs.beforeFirst();

	      		 usuarios = new Usuario(rs.getString("login"), rs.getString("nome"), 
	                		                  rs.getString("email"), rs.getString("senha"));
	          //}
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	public Usuario[] getAll() {
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM usuario");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getString("login"), rs.getString("nome"), 
	                		                  rs.getString("email"), rs.getString("senha"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}

	
	private List<Usuario> readFromFile() {
		usuarios.clear();
		Usuario usuario = null;
		
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				usuario = (Usuario) inputFile.readObject();
				usuarios.add(usuario);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar usuário no disco!");
			e.printStackTrace();
		}
		return usuarios;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Usuario usuario : usuarios) {
				outputFile.writeObject(usuario);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar usuário no disco!");
			e.printStackTrace();
		}
	}

}

