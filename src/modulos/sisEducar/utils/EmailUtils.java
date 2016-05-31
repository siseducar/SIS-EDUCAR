package modulos.sisEducar.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import modulos.sisEducar.om.Email;
 
public class EmailUtils 
{
	/**
	 * Este método deverá ser usado para inicializar as propriedades do email, este método deverá ser chamdo sempre que montarmos
	 * um novo método de envio de email
	 * @return
	 */
	public static Email inicializarPropriedades()
	{
		try 
		{
			Email email = new Email();
			email.setSmtpHostMail("smtp.gmail.com");
			email.setSmtpPortMail("587");
			email.setSmtpAuth("true");
			email.setSmtpStarttls("true");
			email.setUserMail("tccsiseducar@gmail.com");
			email.setFromNameMail("Sis-Educar");
			email.setPassMail("joao123michael123");
			email.setCharsetMail("ISO-8859-1");
			email.setTypeTextMail(Email.TYPE_TEXT_HTML);
			
			return email;
		}
		catch (Exception e) 
		{
			Logs.addFatal("Email", "Falha ao inicializar propriedades");
			return null;
		}
	}
	
    /**
     * Cria as propriedades necessarias para o envio de emails
     * @param mail
     * @return TRUE || FALSE
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    public Boolean enviarEmail(final Email mail) throws UnsupportedEncodingException, MessagingException 
    {
    	try 
    	{
    		Properties props = new Properties();
    		props.setProperty("mail.transport.protocol", "smtp");
    		props.setProperty("mail.host", mail.getSmtpHostMail());
    		props.setProperty("mail.smtp.auth", mail.getSmtpAuth());
    		props.setProperty("mail.smtp.starttls.enable", mail.getSmtpStarttls());
    		props.setProperty("mail.smtp.port", mail.getSmtpPortMail());
    		props.setProperty("mail.mime.charset", mail.getCharsetMail());
    		
    		//classe anonima que realiza a autenticação
    		//do usuario no servidor de email.
    		Authenticator auth = new Authenticator() 
    		{
    			public PasswordAuthentication getPasswordAuthentication() 
    			{
    				return new PasswordAuthentication(mail.getUserMail(), mail.getPassMail());
    			}
    		};
    		
    		// Cria a sessao passando as propriedades e a autenticação
    		Session session = Session.getDefaultInstance(props, auth);
    		// Gera um log no console referente ao processo de envio
    		session.setDebug(true);
    		
    		//cria a mensagem setando o remetente e seus destinatarios
    		Message msg = new MimeMessage(session);
    		//aqui seta o remetente
    		msg.setFrom(new InternetAddress(mail.getUserMail(), mail.getFromNameMail()));
    		boolean first = true;
    		for (Map.Entry<String, String> map : mail.getToMailsUsers().entrySet()) 
    		{
    			if (first) 
    			{
    				//setamos o 1º destinatario
    				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(map.getKey(), map.getValue()));
    				first = false;
    			} 
    			else 
    			{
    				//setamos os demais destinatarios
    				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(map.getKey(), map.getValue()));
    			}
    		}
    		
    		// Adiciona um Assunto a Mensagem
    		msg.setSubject(mail.getSubjectMail());
    		
    		// Cria o objeto que recebe o texto do corpo do email
    		MimeBodyPart textPart = new MimeBodyPart();
    		textPart.setContent(mail.getBodyMail(), mail.getTypeTextMail());
    		
    		// Monta a mensagem SMTP  inserindo o conteudo, texto e anexos
    		Multipart mps = new MimeMultipart();
    		
    		//adiciona o corpo texto da mensagem
    		mps.addBodyPart(textPart);
    		
    		//adiciona a mensagem o conteudo texto e anexo
    		msg.setContent(mps);
    		
    		// Envia a Mensagem
    		Transport.send(msg);
    		
    		return true;
		}
    	catch (Exception e)
    	{
			Logs.addFatal("Email", "Falha ao enviar email");
			return false;
		}
    }
    
    /**
     * Este método é usado para definir um padrão de envio de email
     * @param conteudo (O conteudo é o objetivo do email, o porque de estar sendo enviado)
     * @param conteudoBlocoDois (O conteudoDois aparecera no corpo so que abaixo do segundo bloco)
     * @param ativarBotao (o botão que estamos falando é um botão qualquer que recebera um nome e uma URL para um redirecionamento)
     * @param urlBotao (URL aonde será redirecionado)
     * @param nomeBotao (nome do botão)
     * @return String (Email Pronto)
     */
    public static String emailPadrao(String conteudo, String conteudoBlocoDois,  String urlBotao,  String urlLink, Boolean ativarBotao, String nomeBotao)
    {
    	String corpo = "<html>";
    	corpo += " <td align=\"center\" valign=\"top\">";
    	corpo += " <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color:#fff;background-image:none;background-repeat:repeat\">";
    	corpo += " 	<tbody>";
    	corpo += " 	<tr>";
    	corpo += " 	<td align=\"center\" valign=\"top\">";
    	corpo += " 		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"90\" width=\"100%\" style=\"background-color:#ffffff;background-image:none;background-repeat:repeat\">";
    	corpo += " 			<tbody><tr>";
    	corpo += "			<td align=\"center\" valign=\"middle\">";
    	corpo += "				<a href=\"http://www.siseducar.com\" target=\"_blank\"><img src=\"http://i284.photobucket.com/albums/ll14/jpbonetti/logoSis_zpsg785gftv.png\" border=\"0\" alt=\" photo logoSis_zpsg785gftv.png\"/></a>";
    	corpo += "			</td></tr>";
    	corpo += "		</tbody></table>";
    	corpo += "	</td>";
    	corpo += "	</tr>";
    	corpo += "	<tr>";
    	corpo += "	<td align=\"center\" valign=\"top\">";
    	corpo += "		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"1\" width=\"100%\">";
    	corpo += "			<tbody><tr>";
    	corpo += "				<td align=\"center\" valign=\"middle\" style=\"background-color:#eeeeee\" width=\"249\"></td>";
    	corpo += "				<td align=\"center\" valign=\"middle\" style=\"background-color:#428BCA\" width=\"280\"></td>";
    	corpo += "				<td align=\"center\" valign=\"middle\" style=\"background-color:#eeeeee\" width=\"249\"></td>";
    	corpo += "			</tr>";
    	corpo += "		</tbody></table>";
    	corpo += "	</td>";
    	corpo += "	</tr>";
    	corpo += "	<tr>";
    	corpo += "		<td align=\"center\" valign=\"top\">";
    	corpo += "			<table border=\"0\" cellpadding=\"20\" cellspacing=\"0\" height=\"0\" width=\"100%\">";
    	corpo += "				<tbody><tr><td align=\"center\" valign=\"middle\"></td></tr></tbody></table>";
    	corpo += "		</td>";
    	corpo += "	</tr>";
    	corpo += "	<tr>";
    	corpo += "	<td align=\"center\" valign=\"top\">";
    	corpo += "		<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"0\" width=\"100%\">";
    	corpo += "			<tbody><tr>";
    	corpo += "				<td align=\"center\" valign=\"middle\">";
    	corpo += "					<div style=\"text-align:center;padding:0 20px 20px;font-size:14px;line-height:1.5;width:80%\">";
    	corpo += "						<p>";
    	corpo += 							conteudo;
    	corpo += "						</p>";
    	corpo += "					</div>";
    	corpo += "				</td>";
    	corpo += "			</tr>";
    	corpo += "		</tbody></table>";
    	
    	if(ativarBotao)
    	{
    		corpo += "		<a href=\""+ urlBotao + "\" style=\"background-color:#428BCA;color:#ffffff;display:inline-block;font-family:sans-serif;font-size:14px;line-height:40px;margin-bottom:10px;text-align:center;text-decoration:none;width:200px\" target=\"_blank\"> " + nomeBotao + "</a><br>";
    	}
    	
    	if(conteudoBlocoDois!=null && conteudoBlocoDois.length()>0)
    	{
    		corpo += "		<div style=\"text-align:center;padding:0 20px 20px;font-size:14px;line-height:1.5;width:80%\">";
    		corpo += "			<p> " + conteudoBlocoDois + "</p>";
    		corpo += "		</div>";
    	}
    	
    	if(ativarBotao)
    	{
    		corpo += "		<a href=\"" + urlLink + "\" target=\"_blank\">" + urlBotao + "</a>";
    	}
    	
    	corpo += "	</td>";
    	corpo += "	</tr>";
    	
    	if(ativarBotao)
    	{
    		corpo += "<tr>";
    		corpo += "	<td align=\"center\" valign=\"top\">";
    		corpo += "		<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" height=\"0\" width=\"100%\" style=\"border-top-width:1px;border-top-style:solid;border-top-color:#eee\">";
    		corpo += "	 		<tbody><tr>";
    		corpo += "				<td align=\"center\" valign=\"middle\">";
    		corpo += "					<div>";
    		corpo += "						<small style=\"color:#999;font-size:11px;margin-top:4px;margin-bottom:4px;margin-right:4px;margin-left:4px\">Esta é uma notificação automática, por favor não responda.</small>";
    		corpo += "					</div>";
    		corpo += "				</td>";
    		corpo += "				</tr>";
    		corpo += "			</tbody></table>";
    		corpo += "		</td>";
    		corpo += "	</tr>";
    	}
    	else
    	{
    		corpo += "<tr>";
    		corpo += "	<td align=\"center\" valign=\"top\">";
    		corpo += "		<table border=\"0\" cellpadding=\"30\" cellspacing=\"0\" height=\"0\" width=\"100%\" style=\"border-top-width:1px;border-top-style:solid;border-top-color:#eee\">";
    		corpo += "	 		<tbody><tr>";
    		corpo += "				<td align=\"center\" valign=\"middle\">";
    		corpo += "					<div>";
    		corpo += "						<small style=\"color:#999;font-size:11px;margin-top:4px;margin-bottom:4px;margin-right:4px;margin-left:4px\">Esta é uma notificação automática, por favor não responda.</small>";
    		corpo += "					</div>";
    		corpo += "				</td>";
    		corpo += "				</tr>";
    		corpo += "			</tbody></table>";
    		corpo += "		</td>";
    		corpo += "	</tr>";
    		
    	}
    	
    	corpo += "	</tbody>";
    	corpo += "	</table>";
    	corpo += "</td>";
    	corpo += "</html>";
    	
    	return corpo;
    }
}