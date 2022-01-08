package com.ti2cc;

import java.sql.*;

public class RelatoDAO {
		private Connection conexao;
		
		/*public RelatoDAO() {
			conexao = null;
		}*/
		
		public boolean conectar() {
			String driverName = "org.postgresql.Driver";                    
			String serverName = "localhost";
			String mydatabase = "sosmulheres";
			int porta = 5433;
			String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
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

	public boolean add(Relato relato) {
		boolean status = false;
		
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO relato (id, nome, data, idade, testemunho, categoria) "
					       + "VALUES (" + relato.getId()+ ", '" + relato.getNome() + "', '"  
					       + relato.getData() + "', '" + relato.getIdade() + "', '" 
					       + relato.getTestemunho() + "', '" + relato.getCategoria() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	

	public boolean update(Relato relato) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE relato SET nome = '" + relato.getNome() + "', '"  
				       + relato.getData() + "', '" + relato.getIdade() + "', '" 
				       + relato.getTestemunho() + "', '" + relato.getCategoria() + "'"
					   + " WHERE id = " + relato.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean remove(String id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM relato WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public Relato get(String id) {
		Relato usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM relato WHERE login = " + id);		
	        usuarios = new Relato();
	             
	        usuarios = new Relato(rs.getString("id"), rs.getString("nome"), rs.getDate("data"), 
	        		              rs.getInt("idade"), rs.getString("testemunho"), rs.getString("categoria"));
	     
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	public Relato[] getAll() {
		Relato[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM relato");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Relato[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Relato(rs.getString("id"), rs.getString("nome"), rs.getDate("data"), 
      		              rs.getInt("idade"), rs.getString("testemunho"), rs.getString("categoria"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}
	
	
	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Relato relato : relatos) {
				outputFile.writeObject(relato);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar relato no disco!");
			e.printStackTrace();
		}
	}

}

