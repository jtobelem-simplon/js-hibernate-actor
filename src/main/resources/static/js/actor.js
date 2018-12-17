

// on associe les clicks d'objet html avec des fonctions
$(document).ready(function() {

	//load of datatable
	loadDatatable();

	// dblclik on datatable
	$('#actorTable tbody').on( 'dblclick', 'tr', selectRow);

	//click on PUT
	$("#btn-put").click(updateActor);
	
	//click on POST
	$("#btn-post").click(createActor);
	
	//click on DELETE
	$("#btn-delete").click(deleteActor);
	
	//click on RESET
	$("#btn-reset").click(resetForm);

	//click on GET
	$("#btn-get").click(getActor);

	
});



///////////////////////////////////////////////////////////////////////////////

/**
* loadDatatable
**/
function loadDatatable() {
	$('#actorTable').DataTable({

		"ajax" : {
			url : '/api/actors',
			"dataSrc" : function (json) {
				return json;
			}
		},
		"columns" : [
			{"data" : "id"},
			{"data" : "firstName"},
			{"data" : "lastName"},
			{"data" : "lastUpdate"} ]
	});
}

/**
* select
**/
function selectRow(){
	var table = $('#actorTable').DataTable();
	let dataRow = table.row( this ).data();
	$("#id").val(dataRow.id);
	$("#lastUpdate").val(dataRow.lastUpdate);
	$("#firstname").val(dataRow.firstName);
	$("#lastname").val(dataRow.lastName);
}

/**
* resetForm
**/
function resetForm() {
	$('#actor-form')[0].reset();
	$('#actorTable').DataTable().ajax.reload();
	
}

/**
* create
**/
function createActor() {
	var button = $("#btn-post");

	var actor = {};
	actor["firstName"] = $("#firstname").val();
	actor["lastName"] = $("#lastname").val();
	actor["lastUpdate"] = $("#lastUpdate").val();

	button.prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/api/actor",
		data : JSON.stringify(actor),
		dataType : 'json',
		
		success : function(json) {
			button.prop("disabled", false);
			resetForm()
		}
	});
}

/**
* update
**/
function updateActor() {
	var button = $("#btn-put");

	var actor = {};
	actor["id"] = $("#id").val();
	actor["firstName"] = $("#firstname").val();
	actor["lastName"] = $("#lastname").val();
	actor["lastUpdate"] = $("#lastUpdate").val();

	
	button.prop("disabled", true);

	$.ajax({
		type : "PUT",
		contentType : "application/json",
		url : "/api/actor/"+ actor["id"],
		data : JSON.stringify(actor),
		dataType : 'json',
		
		success : function(json) {
			button.prop("disabled", false);
			resetForm()
		}
	});
}

/**
* getActor
**/
function getActor() {

	var idActor = $("#id").val();

	$.ajax({
		type : "GET",
		url : "/api/actor/" + idActor,
		dataType : 'json',

		success : function(json) {
			$("#id").val(json.id);
			$("#lastUpdate").val(json.lastUpdate);
			$("#firstname").val(json.firstName);
			$("#lastname").val(json.lastName);
		}

	});
}

/**
* deleteActor
**/
function deleteActor() {

	var idActor = $("#id").val();

	$.ajax({
		type : "DELETE",
		url : "/api/actor/" + idActor,

		success : function(json) {
			resetForm();
		}
	});
}
