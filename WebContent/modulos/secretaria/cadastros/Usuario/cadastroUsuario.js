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
	
	/* Table */
	$(document).on('click', 'th input:checkbox' , function(){
		var that = this;
		$(this).closest('table').find('tr > td:first-child input:checkbox')
		.each(function(){
			this.checked = that.checked;
			$(this).closest('tr').toggleClass('selected');
		});
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

/**
 * Usado para esconder a modal pesquisar da tela de cadastro de usuário
 * @author João Paulo
 * @param data
 */
function esconderModalPesquisar(data)
{
	if(data.status=="success")
	{
		$('#myModal').modal('hide');
	}
}

/**
 * Usado para esconder a modal pesquisar da tela de cadastro de usuário
 * @author João Paulo
 * @param data
 */
function aparecerModalDeletarTodos(data)
{
	if(data.status=="success")
	{
		$('#modalDeletarTodos').modal('show');
	}
}
