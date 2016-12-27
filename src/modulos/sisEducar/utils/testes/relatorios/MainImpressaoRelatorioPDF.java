package modulos.sisEducar.utils.testes.relatorios;

import java.util.ArrayList;
import java.util.List;

import modulos.sisEducar.relatorios.Relatorio;
import modulos.sisEducar.utils.ConstantesSisEducar;


public class MainImpressaoRelatorioPDF {

	public static void main(String[] args) throws Exception 
	{
		// lista com os nossos clientes
		List<ClasseTesteImpressaoRelatorioPDF> lista = new ArrayList<ClasseTesteImpressaoRelatorioPDF>();
		
		ClasseTesteImpressaoRelatorioPDF c1 = new ClasseTesteImpressaoRelatorioPDF();
		c1.setNome("Alexandre Macedo");
		c1.setEmail("alexbmac@gmail.com");
		c1.setTelefone("9999-9999");

		ClasseTesteImpressaoRelatorioPDF c2 = new ClasseTesteImpressaoRelatorioPDF();
		c2.setNome("Rafael Cosentino");
		c2.setEmail("cosen@gmail.com");
		c2.setTelefone("8888-8888");

		ClasseTesteImpressaoRelatorioPDF c3 = new ClasseTesteImpressaoRelatorioPDF();
		c3.setNome("Daniel Machado");
		c3.setEmail("daniel@gmail.com");
		c3.setTelefone("7777-7777");

		lista.add(c1);
		lista.add(c2);
		lista.add(c3);

		System.out.println("Gerando relatório de teste...");
		Relatorio.gerarArquivoPDF(lista, "C:\\relatoriosTemp\\RelatoriosClientes.jrxml", 
				ConstantesSisEducar.PATH_PROJETO_JOAO + ConstantesSisEducar.PATH_DESTINO_RELATORIOS_LOCAL, "RelatorioClientes");
		System.out.println("Relatório gerado.");
	}
}
