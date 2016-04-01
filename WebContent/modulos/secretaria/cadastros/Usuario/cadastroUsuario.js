$(function($) {
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
});

/**
 * Método usado para atualizar o campo field escondido na tela com o gênero selecionado na tela de usuário
 * @author João Paulo
 * @param val - gênero(Feminino ou Masculino)
 */
function atualizarGenero(val)
{
	document.getElementById('inputGeneroAux').value = val;
}