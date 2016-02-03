window.jQuery || document.write("<script src='../assets/js/jquery.min.js'>"+"<"+"/script>");
window.jQuery || document.write("<script src='../assets/js/jquery1x.min.js'>"+"<"+"/script>");

jQuery(function($) 
{
	$(document).on('click', '.toolbar a[data-target]', function(e) 
	{
		e.preventDefault();
		var target = $(this).data('target');
		$('.widget-box.visible').removeClass('visible');//hide others
		$(target).addClass('visible');//show target
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

	
