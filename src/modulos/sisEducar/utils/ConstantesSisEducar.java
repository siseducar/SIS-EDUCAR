package modulos.sisEducar.utils;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="sisEducarConstantes")
public class ConstantesSisEducar 
{
	/* Path do projeto, contem o caminho completo até o package sisEducar */
	public static final String PATH_PROJETO_NOME = "/SIS-EDUCAR";
	
	public static String USUARIO_LOGADO = "";

	/* Constante status ativo e removido*/
	public static final int STATUS_ATIVO = 0;
	public static final int STATUS_REMOVIDO = 999;
	
	//Aguardando validação pelo email enviado para o usuário
	public static final int STATUS_INCOMPLETO = 888;
	
	/*Este status é para quando o usuario solicita uma redefinição de senha*/
	public static final int STATUS_REDEFINICAO_SENHA_LIBERADO = 777;
	public static final int STATUS_REDEFINICAO_SENHA_CONCLUIDO = 778;
	
	public static final String GENERO_FEMININO = "feminino";
	public static final String GENERO_MASCULINO = "masculino";
}
