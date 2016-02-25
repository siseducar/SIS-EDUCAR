(function($){999
	$(document).ready(function(){
		$('ul.dropdown-menu [data-toggle=dropdown]').on('click', function(event) {
			event.preventDefault(); 
			event.stopPropagation(); 
			$(this).parent().siblings().removeClass('open');
			$(this).parent().toggleClass('open');
		});
	});
})(jQuery);

$(function() {
    $( ".date" ).datepicker({
    	dateFormat: 'dd/mm/yy',
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
        nextText: 'Próximo',
        prevText: 'Anterior',
    });
  });

$(function() {
	$("#formCadastroPessoa")(){
		$("numCpf").mask("999.999.999-99");
		$("numRg").mask("99.999.999-*");
		$("numTelefone").mask("(99)9999-9999");
		$("numCelular").mask("(99) 9 9999-9999");
		$("numCpfMae").mask("999.999.999-99");
		$("numCpfPai").mask("999.999.999-99");
	};
});

$(function(){
	$("#formCadastroPessoa").validate({
		rules:{
			nomePessoa:{
				required:true
			}
		}
	})
});