package modulos.sisEducar.utils.testes;
 
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import modulos.sisEducar.om.Email;
import modulos.sisEducar.utils.EmailUtils;
 
public class EmailTeste 
{
    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException 
    {
        Email email = EmailUtils.inicializarPropriedades();
        email.setSubjectMail("JavaMail");
        email.setBodyMail(EmailUtils.emailPadrao("Olá USUARIO_NOME, precisamos que você clique no botão abaixo para que sua conta de usuário seja validada.", true, "", "Validar Usuário"));
 
        //sete quantos destinatarios desejar
        Map<String, String> map = new HashMap<String, String>();
        map.put("jpbr.webdesigner@gmail.com", "");
 
        email.setToMailsUsers(map);
 
        new EmailUtils().enviarEmail(email);
    }
 
    private static String textMessage() 
    {
        return  "Leia o novo tutorial JavaMail do Programando com Java.n" +
                "Saiba como enviar emails com anexo, em formato texto e html.n" +
                "Envie seu email para mais de um destinatario.";
    }
 
    private static String htmlMessage() 
    {
        return
        "<html>n" +
        "t<head>n" +
        "tt<title>Email no formato HTML com Javamail!</title> n" +
        "t</head>n" +
        "t<body>n" +
        "tt<div style='background-color:orange; width:28%; height:100px;'>n" +
        "ttt<ul>n" +
        "tttt<li>Leia o novo tutorial JavaMail do Programando com Java.</li>n" +
        "tttt<li>Aprenda como enviar emails com anexos.</li>n" +
        "tttt<li>Aprenda a enviar emails em formato texto simples ou html.</li> n" +
        "tttt<li>Aprenda como enviar seu email para mais de um destinátario.</li>n" +
        "ttt</ul>n" +
        "ttt<p>Visite o blog n" +
        "tttt<a href='http://mballem.wordpress.com/'>Programando com Java</a>n" +
        "ttt</p>n" +
        "tt</div>tn" +
        "tt<div style='width:28%; height:50px;' align='center'>n" +
        "tttDownload do JavaMail<br/>n" +
        "ttt<a href='http://www.oracle.com/technetwork/java/javaee/index-138643.html'>n" +
        "tttt<img src='http://www.oracleimg.com/admin/images/ocom/hp/oralogo_small.gif'/>n" +
        "ttt</a> n" +
        "tt</div>ttn" +
        "t</body> n" +
        "</html>";
    }
}