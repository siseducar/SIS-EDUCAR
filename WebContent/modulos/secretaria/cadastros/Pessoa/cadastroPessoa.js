$("#modal-form").hide();
$("#modal-table").hide();

$(function carrgaElementos() {
	/*	Componente CPF  */
	$(".cpf").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: 000.000.000-00');
        $(this).mask("999.999.999-99");
    });
	$(".cpf").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000"});
		$(this).attr('placeholder','');
        $(this).unmask();
    });
	
	/* Componente RG */
	$(".rg").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: 00.000.000-0');
        $(this).mask("99.999.999-*");
	});
	$(".rg").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000","text-transform":"uppercase"});
        $(this).attr('placeholder','');
        $(this).unmask();
    });
	
	/* Componente NUMERO TELEFONE */
	$(".telResidencial").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: (00) 0000-000')
		$(this).mask("(99) 9999-9999");
	});
	$(".telResidencial").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000"});
        $(this).attr('placeholder','');
        $(this).unmask();
    });
	
	/* Componente NUMERO CELULAR */
	$(".telCelular").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: (00) 0 0000-000')
		$(this).mask("(99) 9 9999-9999");
	});
	$(".telCelular").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000"});
        $(this).attr('placeholder','');
        $(this).unmask();
    });
	
	/* Componente CALENDARIO */
	$(".calendario").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
        $(this).attr('placeholder','Ex.: 00/00/0000');
        $(this).mask("99/99/9999");
    });
	$(".calendario").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000"});
        $(this).attr('placeholder','');
        $(this).unmask();
    });
	
	/* Componente CEP */
	$(".cep").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: 00000-000')
		$(this).mask("99999-999");
	});
	$(".cep").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000"});
        $(this).attr('placeholder','');
        $(this).unmask();
    });
	
	
	/* Componenetes que aceitem somente numeros */
	$('.numeros').keypress(function(event) {
		var tecla = (window.event) ? event.keyCode : event.which;
		if ((tecla > 47 && tecla < 58)) {
			return true;
		} else {
			if (tecla != 8) {				
				return false;
			} else {
				return true;
			}
		}
	});
	
	$(".spinner").ace_spinner({
        min: 0,
        max: 280,
	    step: 1,
	    
	   icon_up: 'fa fa-caret-up',
	   icon_down: 'fa fa-caret-down',
	   on_sides: true
	});
	
});

$(function() {
    $( ".calendario" ).datepicker({
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

$(".upload").ace_file_input({
    style: 'well',
    no_file: "Escolha uma",
    droppable: true, //html5 browsers only
    thumbnail: 'small', //html5 browsers only

    maxSize: 1000000, //~100 KB
    allowExt:  ['jpg', 'jpeg', 'png', 'bmp'],
    allowMime: ['image/jpg', 'image/jpeg', 'image/png', 'image/gif', 'image/tif', 'image/tiff', 'image/bmp'] //html5 browsers only
});

$(".upload").ace_file_input({
    'allowExt': ['jpg', 'jpeg', 'png']
})