package modulos.sisEducar.utils;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="sisEducarConstantes")
public class ConstantesSisEducar 
{
	/* Path do projeto, contem o caminho completo at� o package sisEducar */
	public static final String PATH_PROJETO_NOME = "/SIS-EDUCAR";
	
	public static String USUARIO_LOGADO = "";

	/* Constante status ativo e removido*/
	public static final int STATUS_ATIVO = 0;
	public static final int STATUS_REMOVIDO = 999;
	
	//Aguardando valida��o pelo email enviado para o usu�rio
	public static final int STATUS_INCOMPLETO = 888;
}
