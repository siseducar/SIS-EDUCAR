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
	
	/* Componente CEP */
	$(".cep").on('focus',function(){
		$(this).css({"background-color":"#FFFAFA","color":"#000000"});
		$(this).attr('placeholder','Ex.: 00.000-000')
		$(this).mask("99.999-999");
	});
	$(".cep").blur(function(){
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
		$('#myModal').modal('hide');
	}
}

/**
 * Usado para mostrar a modal remover todas as permissões da tela de cadastro de unidade escolar
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
 * Usado para esconder a modal remover todas as permissões da tela de cadastro de unidade escolar
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
