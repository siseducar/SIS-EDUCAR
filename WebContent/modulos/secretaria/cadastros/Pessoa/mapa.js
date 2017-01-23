var placeSearch, autocomplete;

var componentForm = {
	numCep : 'short_name',
	nomeLogradouro : 'long_name',
	numEndereco : 'short_name'
};

function initAutocomplete() {
	autocomplete = new google.maps.places.Autocomplete(
		/** @type {!HTMLInputElement} */(document.getElementById('txtEndereco')),
		{types: ['geocode']});
	
	autocomplete.addListener('place_changed', fillInAddress);
}

function fillInAddress() {
	var place = autocomplete.getPlace();
	
	for( var component in componentForm ) {
		$(component).val = '';
	    $(component).disabled = true;
	}
	
	for( var i = 0; i < place.address_components.length; i++ ) {
		var addressType = place.address_components[i].types[0];
	    if (componentForm[addressType]) {
	      var value = place.address_components[i][componentForm[addressType]];
	      $(addressType).val(value);
	    }
	}
}

function geolocate() {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			var geolocation = {
	            lat: position.coords.latitude,
	            lng: position.coords.longitude
			};
			var circle = new google.maps.Circle({
				center: geolocation,
				radius: position.coords.accuracy
			});
			autocomplete.setBounds(circle.getBounds());
		});
	}
}