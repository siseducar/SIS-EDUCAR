/* Variveis utilizadas para o uso do Mapa */
var geocoder;
var map;
var marker;

/* Funcão para inicilizar o mapa */
function initialize() {
	/* Ponto inicial do mapa */
	var latlng = new google.maps.LatLng(0, 0);
	
	/* Verifica a atual posicao */
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position){ 
			var latitudeAtual = position.coords.latitude;
			var longitudeAtual = position.coords.longitude;
			marker.setPosition(new google.maps.LatLng(latitudeAtual, longitudeAtual));
			carregarEndereco(latitudeAtual, longitudeAtual);
			latlng = new google.maps.LatLng(latitudeAtual, longitudeAtual);
		}, 
		function(error){ // callback de erro
			alert('Erro ao obter localização!');
			console.log('Erro ao obter localização.', error);
		});
	} else {
		console.log('Navegador não suporta Geolocalização!');
	}
	
	/* Opções relacionadas ao mapa */
	var options = {
		zoom: 13,
		center: latlng,
		mapTypeId: google.maps.MapTypeId.ROADMAP
		
	};
	map = new google.maps.Map(document.getElementById("mapa"), options);
	geocoder = new google.maps.Geocoder();
	marker = new google.maps.Marker({
		map: map,
		draggable: true,
	});
	
	marker.setPosition(latlng);
};

/* Verifica a atual posicao */
if(navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(function(position){ 
		var latitudeAtual = position.coords.latitude;
		var longitudeAtual = position.coords.longitude;
		marker.setPosition(new google.maps.LatLng(latitudeAtual, longitudeAtual));
		carregarEndereco(latitudeAtual, longitudeAtual);
		
	}, 
	function(error){ // callback de erro
		alert('Erro ao obter localização!');
		console.log('Erro ao obter localização.', error);
	});
} else {
	console.log('Navegador não suporta Geolocalização!');
}


$(document).ready(function () {
	initialize();
	function carregarNoMapa(endereco) {
		geocoder.geocode({ 'address': endereco + ', Brasil', 'region': 'BR' }, function (results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				if (results[0]) {
					var latitude = results[0].geometry.location.lat();
					var longitude = results[0].geometry.location.lng();
					
					$('#txtEndereco').val(results[0].formatted_address);
					$('#txtLatitude').val(latitude);
                   	$('#txtLongitude').val(longitude);
		
					var location = new google.maps.LatLng(latitude, longitude);
					marker.setPosition(location);
					map.setCenter(location);
					map.setZoom(16);
				}
			}
		})
	}
	$("#txtEndereco").blur(function() {
		if($(this).val() != "")
			carregarNoMapa($(this).val());
	})
	
	google.maps.event.addListener(marker, 'drag', function () {
		geocoder.geocode({ 'latLng': marker.getPosition() }, function (results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				if (results[0]) {  
					$('#txtEndereco').val(results[0].formatted_address);
					$('#txtLatitude').val(marker.getPosition().lat());
					$('#txtLongitude').val(marker.getPosition().lng());
					carregarEndereco(marker.getPosition().lat(),marker.getPosition().lng());
				}
			}
		});
	});
	
	$("#txtEndereco").autocomplete({
		source: function (request, response) {
			geocoder.geocode({ 'address': request.term + ', Brasil', 'region': 'BR' }, function (results, status) {
				response($.map(results, function (item) {
					return {
						label: item.formatted_address,
						value: item.formatted_address,
						latitude: item.geometry.location.lat(),
          				longitude: item.geometry.location.lng()
					}
				}));
			})
		},
		select: function (event, ui) {
			$("#txtLatitude").val(ui.item.latitude);
    		$("#txtLongitude").val(ui.item.longitude);
			var location = new google.maps.LatLng(ui.item.latitude, ui.item.longitude);
			marker.setPosition(location);
			map.setCenter(location);
			map.setZoom(16);
			carregarEndereco(ui.item.latitude,ui.item.longitude);
		}
	});
});


/* Função que carrega automaticamente os valores dos componentes na tela */
function carregarEndereco(latitude,longitude) {
	var latlng = latitude + "," + longitude;  
	var url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latlng + "&sensor=true";
	
	$.getJSON(url, function( data ) {
		for(var i=0; i < 1; i++) {
			for(var j=0; j< data.results[i].address_components.length; j++){
				if(data.results[i].address_components[j].types == 'street_number'){
					document.getElementById('numCasa').value = data.results[i].address_components[j].long_name;
				}
				if(data.results[i].address_components[j].types == 'postal_code'){
					document.getElementById('numCep').value = data.results[i].address_components[j].long_name;
				}
				if(data.results[i].address_components[j].types == 'route'){
					document.getElementById('nomeLogradouro').value = data.results[i].address_components[j].long_name; 
				}
				if(data.results[i].address_components[j].types == 'sublocality_level_1,sublocality,political'){
					document.getElementById('codBairro').value = data.results[i].address_components[j].long_name; 
				}
				
			}
			
			var adress = data.results[i].formatted_address;
			document.getElementById('txtLatitude').value = latitude;
			document.getElementById('txtLongitude').value = longitude;			
			document.getElementById('txtEndereco').value = adress; 
			
		}
		
	});
}