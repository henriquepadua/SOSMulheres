var db_relatos = {
    "relatos": [
        {
            "Id":"",
            "Nome": "",
            "Idade": "",
            "Data": "",
            "Testemunho": ""
        }
    ]
}; 

function salvaRelato(event) { 
    event.preventDefault();
    let nome = document.getElementById('nomeRel').value;
    let idade = document.getElementById('idadeRel').value;
    let data = document.getElementById('dataRel').value;
    let testemunho = document.getElementById('testemunhoRel').value;
    let id = JSON.parse(sessionStorage.getItem('usuarioCorrente')).id;
    addReport(id,nome, idade, data, testemunho);

    alert('Relato salvo com sucesso. Clique no botão "mais relatos" para acessar o conteúdo');
} 



function addReport(id,nameRel, ageRel, dateRel, reportRel) {
    let relato = { "Id": id, "Nome": nameRel, "Idade": ageRel, "Data": dateRel, "Testemunho": reportRel };
    let obj = localStorage.getItem('db_relatos');
    if(obj == null || obj == "undefined")
        db_relatos.relatos.push(relato);
    else{
        db_relatos = JSON.parse(obj);
        db_relatos.relatos.push(relato);
    }    
    localStorage.setItem('db_relatos', JSON.stringify(db_relatos));
}

var idBotaoEditar = 0;

function getId(e){
    idBotaoEditar = e;
}

function edicaoRelato(event) {
    event.preventDefault();
    let nome = document.getElementById('nomeEdit').value;
    let idade = document.getElementById('idadeEdit').value;
    let data = document.getElementById('dataEdit').value;
    let testemunho = document.getElementById('testemunhoEdit').value;
    let id = JSON.parse(sessionStorage.getItem('usuarioCorrente')).id;
    alterarDados(id, nome, idade, data, testemunho, idBotaoEditar);

    alert('Relato alterado com sucesso!');
}

function alterarDados(id, nome, idade, data, testemunho, idBotaoEditar){
    let relato = { "Id": id, "Nome": nome, "Idade": idade, "Data": data, "Testemunho": testemunho };
    let obj = localStorage.getItem('db_relatos');
    if(obj == null)
        db_relatos.relatos.push(relato);
    else{
        db_relatos = JSON.parse(obj);
        let relatos = db_relatos.relatos[idBotaoEditar];
        relatos.Nome = nome;
        relatos.Idade = idade;
        relatos.Data = data;
        relatos.Testemunho = testemunho;
    }    
    localStorage.setItem('db_relatos', JSON.stringify(db_relatos));   
    document.location.reload(true);

}

function apagarRelato(id){
    let obj = JSON.parse(localStorage.getItem('db_relatos'));
    delete obj.relatos[id];
    localStorage.setItem('db_relatos', JSON.stringify(obj));
    document.location.reload(true);
    }
    
function exibeRelatos() {   
    let listaRelatos = '';
    
    $.ajax({
	 	url: 'http://0.0.0.0:5500/relato',
	 	type: "GET",
	 	dataType: "json"
    }).done(function (db_relatos) {
       // let id = JSON.parse(sessionStorage.getItem('usuarioCorrente')).id;
	  const id = 9;
	  for (i = 1; i < db_relatos.length; i++) {
	        let relatos = db_relatos[i];
	        if(relatos != null){
	            if(relatos.id == id){ // relatos.idUsuario == id
	                listaRelatos += 
	                `<div class = "row" style = "background-color: #fadff3;">
	                    <div class = "col-3 col-sm-12 col-md-6 col-lg-4 col-xl-3">
	                    <center>
	                        <div class="row">
	                            <div class = "col-6">
	                                <button type="button" onClick="getId(this.id)" id="${i}" class="btn editar" data-bs-toggle="modal" data-bs-target="#editar"><i class="far fa-edit" style="color:black;"></i></button>
	                            </div>
	                            <div class="col-6">    
	                                <button type="button" onClick="apagarRelato(this.id)" id="${i}" class="btn apagar"><i class="far fa-trash-alt" style="color:black;"></i></button>                            
	                            </div>
	                        </div>
	                        <span class="badge bg-dark icone"><i class="fas fa-user"></i></span>
	                        <h5 style="color: black;" class="mt-4 tit-papa"><strong>${relatos.nome}</strong></h5>
	                        <p>Idade na época: ${relatos.idade}<br>Data do ocorrido: ${relatos.data}</p>
	                    </center>
	                    </div>
	                    <div class = "col-9 col-sm-12 col-md-6 col-lg-8 col-xl-9"><br>
	                        <p>${relatos.testemunho}</p>
	                    </div>
	                </div>
	                <br>`;
	            }else{
	                listaRelatos += 
	                `<div class = "row" style = "background-color: #fadff3;">
	                    <div class = "col-3 col-sm-12 col-md-6 col-lg-4 col-xl-3">
	                    <center>
	                        <span class="badge bg-dark icone"><i class="fas fa-user"></i></span>
	                        <h5 style="color: black;" class="mt-4 tit-papa"><strong>${relatos.nome}</strong></h5>
	                        <p>Idade na época: ${relatos.idade}<br>Data do ocorrido: ${relatos.data}</p>
	                    </center>
	                    </div>
	                    <div class = "col-9 col-sm-12 col-md-6 col-lg-8 col-xl-9"><br>
	                        <p>${relatos.testemunho}</p>
	                    </div>
	                </div>
	                <br>`;
	            }
	        }
	    }
	    
    	document.getElementById("testemunhos").innerHTML = listaRelatos;

	})  
}

$(document).ready(function() {
	exibeRelatos();
});
 
 