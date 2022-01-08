package com.ti2cc;

import static spark.Spark.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
	
public class Aplicacao {
	
	private static RelatoService relatoService = new RelatoService();
	private static UsuarioService usuarioService = new UsuarioService();
	
    public static void main(String[] args) throws Exception{
        port(4567);
        //staticFiles.location("/");
        
        RetornaroFront mandarSite = new RetornaroFront();
        UsuarioDAO sosmulheres = new UsuarioDAO();
        sosmulheres.conectar();
        
        post("/login", (request, response) -> usuarioService.login(request, response));        
        
        post("/relato", (request, response) -> relatoService.add(request, response));

        get("/relato/:id", (request, response) -> relatoService.get(request, response));

        get("/relato/update/:id", (request, response) -> relatoService.update(request, response));

        get("/relato/delete/:id", (request, response) -> relatoService.remove(request, response));

        get("/relato", (request, response) -> relatoService.getAll(request, response));
        
        post("/usuario", (request, response) -> usuarioService.add(request, response));

        get("/usuario/:login", (request, response) -> usuarioService.get(request, response));

        get("/usuario/update/:login", (request, response) -> usuarioService.update(request, response));

        get("/usuario/delete/:login", (request, response) -> usuarioService.remove(request, response));

        get("/", (req,resp) -> mandarSite.renderContent("/index.html"));
        
        //get("/usuario", (request, response) -> usuarioService.getAll(request, response));
        	    
    }
   
}

class RetornaroFront{
	public String renderContent(String htmlFile) throws IOException, URISyntaxException{
		return new String(Files.readAllBytes(Paths.get(getClass().getResource(htmlFile).toURI())),
                StandardCharsets.UTF_8);
	}
}
