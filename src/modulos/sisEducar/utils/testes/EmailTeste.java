package modulos.sisEducar.utils.testes;
 
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import modulos.sisEducar.om.Email;
import modulos.sisEducar.sisEducarServlet.SisEducarServlet;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.EmailUtils;
 
public class EmailTeste 
{
    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException 
    {
        Email email = EmailUtils.inicializarPropriedades();
        String urlBotaoLink = ConstantesSisEducar.PATH_PROJETO_NOME + "/validacao?";
        
        urlBotaoLink += SisEducarServlet.criptografarURL(true, "teste@teste");
		
		email = EmailUtils.inicializarPropriedades();
		email.setSubjectMail("Valida��o de registro SIS-EDUCAR");
		email.setBodyMail(EmailUtils.emailPadrao(" <p style=\"text-align:left; font-size:17px; \">Ol� " + "Jo�o P" + ",</p> " + 
				" <p style=\"text-align:left; font-size:17px; \">A sua solicita��o de cadastro foi realizada com sucesso.</p> " + 
				" <p style=\"font-style:italic; font-size:17px; text-align:left;\"><b>Para que o cadastro seja efetivado clique no bot�o abaixo.</b></p>", "<p style=\"font-style:italic; font-size:17px; text-align:left;\">Caso o bot�o acima n�o funcione clique no link abaixo:</p>", urlBotaoLink, urlBotaoLink, true, "Ativar Usu�rio"));
 
        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        map.put("jpbr.webdesigner@gmail.com", "");
 
        email.setToMailsUsers(map);
 
        new EmailUtils().enviarEmail(email);
    }
}