package modulos.sisEducar.sisEducarServlet;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

import org.apache.tomcat.util.codec.binary.Base64;

import sun.misc.BASE64Encoder;

@RequestScoped
@ManagedBean(name="sisEducarServlet")
public class SisEducarServlet implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private static String parametroUsuarioURL = null;
	
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
	
	/**
	 * Método responsavel por criptografar um conteudo como por exemplo(algum conteudo a ser concatenado em uma URL)
	 * @param criptografar
	 * @param conteudo
	 * @return String
	 */
	public static String criptografarURL(Boolean criptografar, String conteudo)
	{
		try 
		{
			byte[] encodedBytes = Base64.encodeBase64(conteudo.getBytes());
			byte[] decodedBytes = Base64.decodeBase64(encodedBytes);

			if(criptografar)
			{
				return new String(encodedBytes);
			}
			else
			{
				return new String(decodedBytes);
			}
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	/**
	 * Método usado para obter o parametro da URL de validacao de usuario
	 * @return
	 */
	public void getParameterURL()
	{	
		try 
		{
			parametroUsuarioURL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("validacao"); 
			System.out.println(parametroUsuarioURL);
		}
		catch (Exception e) 
		{
			Logs.addFatal("getParameterURL", "Falha ao obter URL");
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

	public static String getParametroUsuarioURL() {
		return parametroUsuarioURL;
	}

	public static void setParametroUsuarioURL(String parametroUsuarioURL) {
		SisEducarServlet.parametroUsuarioURL = parametroUsuarioURL;
	}
}
