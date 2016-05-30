package sisEdcuar.servlet;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;

import modulos.secretaria.dao.UsuarioDAO;
import modulos.secretaria.om.Usuario;
import sisEdcuar.utils.ConstantesSisEducar;
import sisEdcuar.utils.Logs;
import sun.misc.BASE64Encoder;

@RequestScoped
@ManagedBean(name="sisEducarServlet")
public class SisEducarServlet implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	private static String parametroUsuarioURL = null;
	private static String parametroUsuarioURLDescriptografado = null;
	
	private static String parametroUsuarioURLRedefinicaoSenha = null;
	private static String parametroUsuarioURLDescriptografadoRedefinicaoSenha = null;
	
	public static Usuario usuarioLogado = null;
	
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
			byte[] decodedBytes = Base64.decodeBase64(conteudo.getBytes());

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
			Usuario usuario = null;
			java.util.Date dataAtualPermitida = new java.util.Date();
			Date dataCadastro = null;
			Boolean resultadoUpdateUsuario = false;
			
			parametroUsuarioURLDescriptografado = "";
			//Aqui eu pego o conteudo do parametro
			parametroUsuarioURL = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("validacao"); 
			
			//Depois que eu pegar o conteudo do parametro eu descriptografo para obter o email do usuario
			parametroUsuarioURLDescriptografado = criptografarURL(false, parametroUsuarioURL);
			
			usuario = new UsuarioDAO().obtemUsuario(parametroUsuarioURLDescriptografado, ConstantesSisEducar.STATUS_INCOMPLETO);
			
			if(usuario!=null)
			{
				dataCadastro = usuario.getDataLancamento();
				
				//Aqui eu subtraio 2 dias (48 horas) da data atual para ver se o link expirou
				dataAtualPermitida.setDate(dataAtualPermitida.getDate() - 2);
				
				if(!dataCadastro.before(dataAtualPermitida))
				{
					resultadoUpdateUsuario = new UsuarioDAO().atualizarUsuarioIncompleto(usuario.getPkUsuario());
					
					if(resultadoUpdateUsuario)
					{
						FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/login/login.xhtml");
					}
				}
			}
		}
		catch (Exception e) 
		{
			Logs.addFatal("getParameterURL", "Falha ao obter URL");
		}
	}
	
	/**
	 * Este método é usado para obter o parametro da tela de redefinição de senha para que seja possivel concluir o processo de redefinição de senha
	 */
	public void getParameterURLRedefinicaoSenha()
	{
		try 
		{
			Usuario usuario = null;
			//Aqui eu pego o conteudo do parametro
			parametroUsuarioURLRedefinicaoSenha = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("redefinir"); 
			
			//Depois que eu pegar o conteudo do parametro eu descriptografo para obter o email do usuario
			parametroUsuarioURLDescriptografadoRedefinicaoSenha = criptografarURL(false, parametroUsuarioURLRedefinicaoSenha);
			
			usuario = new UsuarioDAO().obtemUsuario(parametroUsuarioURLDescriptografadoRedefinicaoSenha, ConstantesSisEducar.STATUS_ATIVO);
			
			if(usuario!=null)
			{
				putSessionObject(ConstantesSisEducar.USUARIO_LOGADO, usuario);
			}
			else
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect(ConstantesSisEducar.PATH_PROJETO_NOME + "/redefinirSenhaErro.xhtml");
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("getParameterURLRedefinicaoSenha", "");
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

	public static String getParametroUsuarioURLRedefinicaoSenha() {
		return parametroUsuarioURLRedefinicaoSenha;
	}

	public static void setParametroUsuarioURLRedefinicaoSenha(
			String parametroUsuarioURLRedefinicaoSenha) {
		SisEducarServlet.parametroUsuarioURLRedefinicaoSenha = parametroUsuarioURLRedefinicaoSenha;
	}

	public static String getParametroUsuarioURLDescriptografadoRedefinicaoSenha() {
		return parametroUsuarioURLDescriptografadoRedefinicaoSenha;
	}

	public static void setParametroUsuarioURLDescriptografadoRedefinicaoSenha(
			String parametroUsuarioURLDescriptografadoRedefinicaoSenha) {
		SisEducarServlet.parametroUsuarioURLDescriptografadoRedefinicaoSenha = parametroUsuarioURLDescriptografadoRedefinicaoSenha;
	}
}
