package modulos.sisEducar.utils.testes;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import modulos.sisEducar.servlet.SisEducarServlet;

public class TesteSenha {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		String senhaCriptografada = "";
		senhaCriptografada = SisEducarServlet.criptografarSenha("123");
		System.out.println(senhaCriptografada);
	}
}
