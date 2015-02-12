package modulos.sisEducar.utils;

import javax.faces.application.FacesMessage;

import org.primefaces.context.RequestContext;

public class Logs 
{
	/**
	 * Este método é usado para exibir uma dialog na tela para o usuario, o titulo e a descricao da dialog sera recebido como parametro
	 * @param mensagemTitulo
	 * @param mensagemDescricao
	 */
	public static void addInfo(String mensagemTitulo, String mensagemDescricao)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagemTitulo, mensagemDescricao);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
	
	/**
	 * Este método é usado para exibir uma dialog na tela para o usuario, o titulo e a descricao da dialog sera recebido como parametro
	 * @param mensagemTitulo
	 * @param mensagemDescricao
	 */
	public static void addError(String mensagemTitulo, String mensagemDescricao)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagemTitulo, mensagemDescricao);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
	
	/**
	 * Este método é usado para exibir uma dialog na tela para o usuario, o titulo e a descricao da dialog sera recebido como parametro
	 * @param mensagemTitulo
	 * @param mensagemDescricao
	 */
	public static void addWarning(String mensagemTitulo, String mensagemDescricao)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagemTitulo, mensagemDescricao);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
	
	/**
	 * Este método é usado para exibir uma dialog na tela para o usuario, o titulo e a descricao da dialog sera recebido como parametro
	 * @param mensagemTitulo
	 * @param mensagemDescricao
	 */
	public static void addFatal(String mensagemTitulo, String mensagemDescricao)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, mensagemTitulo, mensagemDescricao);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
}
