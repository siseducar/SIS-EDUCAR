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

/**
 * Usado para esconder a modal pesquisar da tela de cadastro de usuário
 * @author João Paulo
 * @param data
 */
function esconderModalPesquisar(data)
{
	if(data.status=="success")
	{
		$('#myModalPesquisar').modal('hide');
	}
}

/**
 * Usado para mostrar a modal remover todas as aulas da tela de cadastro de horario
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

/**
 * Usado para esconder a modal remover todas as aulas da tela de cadastro de horario
 * @author João Paulo
 * @param data
 */
function esconderModalDeletarTodos(data)
{
	if(data.status=="success")
	{
		$('#modalDeletarTodos').modal('hide');
	}
}