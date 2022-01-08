package com.ti2cc;

import java.io.IOException;

import static spark.Spark.*;
import spark.Request;
import spark.Response;


public class UsuarioService {

	private UsuarioDAO usuarioDAO;

	public UsuarioService() {
		
	}

	public Object add(Request request, Response response) {
		String login = request.queryParams("login");
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		String senha = request.queryParams("senha");

		Usuario usuario = new Usuario(login, nome, email, senha);
		System.out.println("ok");
		usuarioDAO.add(usuario);
		
		response.status(201); // 201 Created
		return login;
	}

	public Object get(Request request, Response response) {
		String login = request.params(":login");
		
		Usuario usuario = (Usuario) usuarioDAO.get(login);
		
		if (usuario != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<usuario>\n" + 
            		"\t<login>" + usuario.getLogin() + "</login>\n" +
            		"\t<nome>" + usuario.getNome() + "</nome>\n" +
            		"\t<email>" + usuario.getEmail() + "</email>\n" +
            		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
            		"</usuario>\n";
        } else {
            response.status(404); // 404 Not found
            return "Usuário " + login + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        String login = request.params(":login");
        
		Usuario usuario = (Usuario) usuarioDAO.get(login);

        if (usuario != null) {
        	usuario.setLogin(request.queryParams("login"));
        	usuario.setNome(request.queryParams("nome"));
        	usuario.setEmail(request.queryParams("email"));
        	usuario.setSenha(request.queryParams("senha"));
        	
        	usuarioDAO.update(usuario);
        	
            return login;
        } else {
            response.status(404); // 404 Not found
            return "Usuário não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        String login = request.params(":login");

        Usuario usuario = (Usuario) usuarioDAO.get(login);

        if (usuario != null) {

            usuarioDAO.remove(login);

            response.status(200); // success
        	return login;
        } else {
            response.status(404); // 404 Not found
            return "Usuário não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<usuario type=\"array\">");
		for (Usuario usuario : usuarioDAO.getAll()) {
			returnValue.append("\n<usuario>\n" + 
					"\t<login>" + usuario.getLogin() + "</login>\n" +
		    		"\t<nome>" + usuario.getNome() + "</nome>\n" +
		    		"\t<email>" + usuario.getEmail() + "</email>\n" +
		    		"\t<senha>" + usuario.getSenha() + "</senha>\n" +
		    		"</usuario>\n");
		}
		returnValue.append("</usuario>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
	
	public Object login(Request request, Response response) {
		String userName = request.queryParams("username");
		String senha = request.queryParams("password");
		for (Usuario usuario : usuarioDAO.getAll()) {
			if(usuario.getLogin().equals(userName) && usuario.getSenha().equals(senha)) {
				response.status(200);
				response.redirect("index.html");
				return "ok";
			}
		}
		
		response.status(401);
		return "falso";
	}
}