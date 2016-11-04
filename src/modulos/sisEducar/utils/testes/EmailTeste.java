package modulos.sisEducar.utils.testes;
 
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import modulos.sisEducar.om.Email;
import modulos.sisEducar.servlet.SisEducarServlet;
import modulos.sisEducar.utils.EmailUtils;
 
public class EmailTeste extends SisEducarServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException 
    {
        Email email = EmailUtils.inicializarPropriedades();
        String urlBotaoLink = "http://localHost:8080/SIS-EDUCAR/validacaoUsuario.xhtml?validacao=";
        
        urlBotaoLink += SisEducarServlet.criptografarURL(true, "teste@teste");
		
		email = EmailUtils.inicializarPropriedades();
		email.setSubjectMail("Validação de registro CORUJA");
		email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Olá " + "João P" + ",</p> " + 
				" <p style=\"text-align:left; font-size:17px; \">A sua solicitação de cadastro foi realizada com sucesso.</p> " + 
				" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no botão abaixo. Atenção o link irá expirar em 48 horas.</b></p>", "<p style=\"font-style:italic; font-size:17px; text-align:left;\">Caso o botão acima não funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usuário"));
        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        map.put("jpbr.webdesigner@gmail.com", "");
 
        email.setToMailsUsers(map);
 
        new EmailUtils().enviarEmail(email);
    }
}