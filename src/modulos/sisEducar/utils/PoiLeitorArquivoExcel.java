package modulos.sisEducar.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import modulos.RH.om.Aluno;
import modulos.RH.om.Pessoa;
import modulos.RH.om.UnidadeEscolar;
import modulos.RH.utils.ConstantesRH;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiLeitorArquivoExcel 
{
	public static void main(String[] args) 
	{
		try 
		{
			FileInputStream fileInputStream = new FileInputStream("D:\\CADASTRO ALUNO.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("Plan1");
			HSSFRow linha = null;
			HSSFCell coluna = null;
			Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
			List<Object> listAux = new ArrayList<Object>();
			
			for (int i = 1; i < worksheet.getLastRowNum(); i++) 
			{
				listAux = new ArrayList<Object>();
				
				//Obtem a linha
				linha = worksheet.getRow(i);
				
				coluna = linha.getCell((int) 0); //COD
				listAux.add(coluna);
				
				coluna = linha.getCell((int) 1); //UNIDADE
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 2); //NOME
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 3); //SEXO
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 4); //DTNASCIMENTO
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 5); //RA1
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 6); //RA2
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 7); //UF
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 8); //NOME PAI
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 9); //NOME MÃE
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 10); //NOME RUA
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 11); //NúMERO
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 12); //BAIRRO
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 13); //CEP
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 14); //CIDADE
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 15); //FOLHA
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 16); //LIVRO
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 17); //REGISTRO
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 18); //CIDADENA SC
				listAux.add(coluna);
				
				coluna = linha.getCell((short) 19); //LIVRO UF
				listAux.add(coluna);
				
				map.put(i, listAux);
			}
			
			Integer countPosicao = 0;
			List<Object> listaAuxiliar = null;
			for (Map.Entry<Integer, List<Object>> entry : map.entrySet()) 
			{
				Aluno aluno = new Aluno();
				UnidadeEscolar unidadeEscolar = new UnidadeEscolar();
				Pessoa pessoa = new Pessoa();
				listaAuxiliar = new ArrayList<Object>();
				
				listaAuxiliar = entry.getValue();
				
				//Monta a unidade
				unidadeEscolar.setCodigo((String)listaAuxiliar.get(1)); //Cod Unidade
				unidadeEscolar.setNome("nd");
				unidadeEscolar.setStatus(ConstantesSisEducar.STATUS_ATIVO);
				
				//Monta o aluno
				aluno.setRa((String)listaAuxiliar.get(4)); //RA
				aluno.setRa2((String)listaAuxiliar.get(5)); //RA2
				//6 UF
				
				
				//Monta a pessoa
				pessoa.setNome((String)listaAuxiliar.get(2)); //Nome
				pessoa.setSexo((String)listaAuxiliar.get(3)); //Sexo
				
				//UF - Criar OM Estado
				//Cidade - Criar OM Cidade
				pessoa.setEndereco((String)listaAuxiliar.get(10));
				pessoa.setEnderecoNumero((String)listaAuxiliar.get(11));
				
				listaAuxiliar.get(1);
			    pessoa.setUnidadeEscolar(unidadeEscolar);
			    
			    countPosicao ++;
			}
			
			System.out.println(map.get(1));
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) 			{ e.printStackTrace(); }
	}
}