package com.ti2cc;

import java.io.IOException;
import java.util.Date;

import spark.Request;
import spark.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class RelatoService {

	private RelatoDAO relatoDAO;

	public RelatoService() {
		
	}

	public Object add(Request request, Response response) throws ParseException {
		String id = request.queryParams("id");
		String nome = request.queryParams("nome");
	    Date data = new SimpleDateFormat("dd/MM/yyyy").parse(request.queryParams("data"));
		int idade = Integer.parseInt(request.queryParams("idade"));
		String testemunho = request.queryParams("testemunho");
		String categoria = request.queryParams("categoria");

		Relato relato = new Relato(id, nome, data, idade, testemunho, categoria);

		relatoDAO.add(relato);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		String id = request.queryParams(":id");
		
		Relato relato = (Relato) relatoDAO.get(id);
		
		if (relato != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<relato>\n" + 
            		"\t<id>" + relato.getId() + "</id>\n" +
            		"\t<nome>" + relato.getNome() + "</nome>\n" +
					"\t<data>" + relato.getData() + "</data>\n" +
					"\t<idade>" + relato.getIdade() + "</idade>\n" +
            		"\t<testemunho>" + relato.getTestemunho() + "</testemunho>\n" +
            		"\t<categoria>" + relato.getCategoria() + "</categoria>\n" +
            		"</relato>\n";
        } else {
            response.status(404); // 404 Not found
            return "Relato não encontrado.";
        }

	}

	public Object update(Request request, Response response) throws ParseException {
        String id = request.queryParams(":id");
        
		Relato relato = (Relato) relatoDAO.get(id);

        if (relato != null) {
        	relato.setId(request.queryParams("id"));
        	relato.setNome(request.queryParams("nome"));
        	relato.setData(new SimpleDateFormat("dd/MM/yyyy").parse(request.queryParams("data")));
			relato.setIdade(Integer.parseInt(request.queryParams("idade")));
        	relato.setTestemunho(request.queryParams("testemunho"));
        	relato.setCategoria(request.queryParams("categoria"));
        	
        	relatoDAO.update(relato);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Relato não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        String id = request.queryParams(":id");

        Relato relato = (Relato) relatoDAO.get(id);

        if (relato != null) {

            relatoDAO.remove(id);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Relato não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<relato type=\"array\">");
		for (Relato relato : relatoDAO.getAll()) {
			returnValue.append("\n<relato>\n" + 
					"\t<id>" + relato.getId() + "</id>\n" +
		    		"\t<nome>" + relato.getNome() + "</nome>\n" +
					"\t<data>" + relato.getData() + "</data>\n" +
					"\t<idade>" + relato.getIdade() + "</idade>\n" +
		    		"\t<testemunho>" + relato.getTestemunho() + "</testemunho>\n" +
		    		"\t<categoria>" + relato.getCategoria() + "</categoria>\n" +
		    		"</relato>\n");
		}
		returnValue.append("</relato>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}