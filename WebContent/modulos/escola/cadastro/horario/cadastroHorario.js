$(function carregaElementos() {
	/*	Componente CPF  */
	$(".HoraMinuto").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: HH:MM');
        $(this).mask("99:99");
    });
	$(".HoraMinuto").blur(function(){
		$(this).css({"background-color":"#FFFFFF","color":"#000000"});
		$(this).attr('placeholder','');
        $(this).unmask();
    });
});