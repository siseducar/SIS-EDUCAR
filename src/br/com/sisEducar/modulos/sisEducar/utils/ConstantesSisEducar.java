package br.com.sisEducar.modulos.sisEducar.utils;

import javax.faces.bean.ManagedBean;

@ManagedBean(name="sisEducarConstantes")
public class ConstantesSisEducar 
{
	/* Path do projeto, contem o caminho completo at� o package sisEducar */
	public static final String PATH_PROJETO = "/SIS-EDUCAR/br/com/sisEducar/";
	
	/* Constante status ativo e removido*/
	public static final int STATUS_ATIVO = 0;
	public static final int STATUS_REMOVIDO = 999;
}
