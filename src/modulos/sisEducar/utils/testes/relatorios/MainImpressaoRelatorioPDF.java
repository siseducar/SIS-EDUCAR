package modulos.sisEducar.utils.testes.relatorios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Pessoa;
import modulos.sisEducar.relatorios.Relatorio;
import modulos.sisEducar.utils.ConstantesSisEducar;


public class MainImpressaoRelatorioPDF {

	public static void main(String[] args) throws Exception 
	{
		// lista com os nossos clientes
		List<ClasseTesteImpressaoRelatorioPDF> lista = new ArrayList<ClasseTesteImpressaoRelatorioPDF>();
		List<Pessoa> pessoas = new PessoaDAO().obtemTodos();
		ClasseTesteImpressaoRelatorioPDF classeTesteImpressaoRelatorioPDF = null;
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
		
		for (Pessoa pessoa : pessoas) 
		{
			classeTesteImpressaoRelatorioPDF = new ClasseTesteImpressaoRelatorioPDF();
			classeTesteImpressaoRelatorioPDF.setNome(pessoa.getNome());
			
			if(pessoa.getCpf()!=null) 				{ classeTesteImpressaoRelatorioPDF.setCpf(pessoa.getCpf().toString()); }
			if(pessoa.getSexo().equals("M")) 		{ classeTesteImpressaoRelatorioPDF.setSexo("Masculino"); }
			else 							 		{ classeTesteImpressaoRelatorioPDF.setSexo("Feminino"); }
			if(pessoa.getDataNascimento()!=null)	{ classeTesteImpressaoRelatorioPDF.setDataNascimento(dataFormatada.format(pessoa.getDataNascimento())); }
			
			lista.add(classeTesteImpressaoRelatorioPDF);
		}
		
		System.out.println("Gerando relatório de teste...");
		Relatorio.gerarArquivoPDF(lista, "C:\\relatoriosTemp\\RelatoriosClientes.jrxml", 
				ConstantesSisEducar.PATH_DESTINO_RELATORIOS_LOCAL + "Mogi Mirim\\Pessoa\\", "RelatorioClientes");
		System.out.println("Relatório gerado.");
	}
}
