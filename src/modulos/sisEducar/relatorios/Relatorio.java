package modulos.sisEducar.relatorios;

import java.io.File;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Relatorio 
{
	/**
	 * Método usado para imprimir um relatório no formato PDF
	 * @param informacoes - Recebe qualquer tipo de lista de OM, ex: List<Cliente>, List<Pessoa>, List<Aluno>
	 * @param pathJRXML - Path aonde o arquivo JRXML do relatório se encontra, ex: C:\\relatoriosTemp\\RelatoriosClientes.jrxml
	 * @param pathDestino - Path de destino, ou seja, aonde será criado o arquivo PDF ex: C:\\relatoriosTemp\\relatórios\RelatorioExemplo.pdf
	 * @throws JRException
	 */
	public static String gerarArquivoPDF(List<?> informacoes, String pathJRXML, String pathDestino, String nomeArquivo) throws JRException
	{
		// COMPILACAO DO JRXML
		JasperReport report = JasperCompileManager.compileReport(pathJRXML);
		
		// PREENCHIMENTO DO RELATORIO, NOTE QUE O METODO RECEBE 3 PARAMETROS:
		// 1 - O RELATORIO
		//
		// 2 - UM MAP, COM PARAMETROS QUE SAO PASSADOS AO RELATORIO
		// NO MOMENTO DO PREENCHIMENTO. NO NOSSO CASO EH NULL, POIS NAO
		// ESTAMOS USANDO NENHUM PARAMETRO
		//
		// 3 - O DATA SOURCE. NOTE QUE NAO DEVEMOS PASSAR A LISTA DIRETAMENTE,
		// E SIM "TRANSFORMAR" EM UM DATA SOURCE UTILIZANDO A CLASSE
		// JRBEANCOLLECTIONDATASOURCE
		JasperPrint print = JasperFillManager.fillReport(report, null,
		new JRBeanCollectionDataSource(informacoes));

		pathDestino += File.separator + nomeArquivo + ".pdf";
		
		// EXPORTACAO DO RELATORIO PARA OUTRO FORMATO, NO CASO PDF
		JasperExportManager.exportReportToPdfFile(print, pathDestino);
		
		return pathDestino;
	}
}
