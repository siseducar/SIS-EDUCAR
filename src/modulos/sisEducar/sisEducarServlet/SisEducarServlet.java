package modulos.sisEducar.sisEducarServlet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import modulos.sisEducar.utils.ConstantesSisEducar;
import sun.misc.BASE64Encoder;

@SessionScoped
public class SisEducarServlet 
{
	/**
	 * Gera uma chave de acesso criptografada
	 * @param chaveOriginal
	 * @return
	 */
	public static String gerarChaveAcessoCriptografada(String chaveOriginal)
	{
		try 
		{
			String chaveCriptografada = "";
			
			if(chaveOriginal!=null && chaveOriginal.length() >0)
			{
				chaveOriginal += System.currentTimeMillis();
				chaveCriptografada = criptografarSenha(chaveOriginal);
				
				return chaveCriptografada;
			}
			
			return null;
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	/**
	 * O método é usado para criptografar senhas
	 * @param senha sem modificações
	 * @return senha modificada
	 */
	public static String criptografarSenha (String senha) 
	{     
		try 
		{
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(senha.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(digest.digest());
		} 
		catch (NoSuchAlgorithmException ns) 
		{
			return null;
		}      
	}
	
	public Object getSessionObject(String chave)
	{
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(chave);
	}
	
	public Object getSessionObject(HttpServletRequest req, String chave)
	{
		return req.getSession().getAttribute(ConstantesSisEducar.USUARIO_LOGADO);
	}
	
	public void putSessionObject(String chave, Object object)
	{
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(chave, object);
	}
}
