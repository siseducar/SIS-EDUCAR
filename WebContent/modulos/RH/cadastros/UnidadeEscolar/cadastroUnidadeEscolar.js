
$( "#numTelefone" ).on( "keydown", function(event) {
         alert("Entered!");
    });

$('#numTelefone').keyup(function() {
	  alert($(this).val());
	});