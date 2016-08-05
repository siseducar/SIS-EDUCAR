package modulos.sisEducar.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import modulos.educacao.dao.AlunoDAO;
import modulos.educacao.om.Aluno;
import modulos.secretaria.dao.CidadeDAO;
import modulos.secretaria.dao.EnderecoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.UnidadeEscolar;

public class PoiLeitorArquivoExcel 
{
	public static void main(String[] args) throws SQLException 
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
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((int) 1); //UNIDADE
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 2); //NOME
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 3); //SEXO
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 4); //DTNASCIMENTO
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 5); //RA1
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 6); //RA2
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 7); //UF
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 8); //NOME PAI
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 9); //NOME M�E
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 10); //NOME RUA
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 11); //NÚMERO
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 12); //BAIRRO
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 13); //CEP
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 14); //CIDADE
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 15); //FOLHA
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 16); //LIVRO
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 17); //REGISTRO
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 18); //CIDADENA SC
				listAux.add(coluna.toString());
				
				coluna = linha.getCell((short) 19); //LIVRO UF
				listAux.add(coluna.toString());
				
				map.put(i, listAux);
			}
			
			Integer countPosicao = 0;
			List<Object> listaAuxiliar = null;
			Aluno aluno = null;
			UnidadeEscolar unidadeEscolar = null;
			Pessoa pessoa = null;
			Endereco endereco = null;
			Cidade cidade = null;
			Cidade cidadeNascimento = null;
			AlunoDAO alunoDAO = new AlunoDAO();
			PessoaDAO pessoaDAO = new PessoaDAO();
			EnderecoDAO enderecoDAO = new EnderecoDAO();
			CidadeDAO cidadeDAO = new CidadeDAO();
			UnidadeEscolarDAO unidadeEscolarDAO = new UnidadeEscolarDAO();
			for (Map.Entry<Integer, List<Object>> entry : map.entrySet()) 
			{
				
				aluno = new Aluno();
				unidadeEscolar = new UnidadeEscolar();
				pessoa = new Pessoa();
				endereco = new Endereco();
				listaAuxiliar = new ArrayList<Object>();
				listaAuxiliar = entry.getValue();
				String stringAux = "";
				cidade = new Cidade();
				cidadeNascimento = new Cidade();
				
				//Aqui eu retorno apenas a PK da cidade
				if(!listaAuxiliar.get(14).equals("NULL")) { cidade.setPkCidade(cidadeDAO.obtemPKCidade(null, (String)listaAuxiliar.get(14))); }
				else { cidade = null; }
				
				//Monta a unidade
				unidadeEscolar.setCodigo(cortarCasasDecimais((String)listaAuxiliar.get(1), false, false));
				unidadeEscolar.setNome("nd");
				unidadeEscolar.setStatus(ConstantesSisEducar.STATUS_ATIVO);
				
				unidadeEscolar = unidadeEscolarDAO.inserirUnidadeEscolar(unidadeEscolar);
				
				//Monta o endereço
				if(!listaAuxiliar.get(10).equals("NULL")) { endereco.setLogradouro((String)listaAuxiliar.get(10)); }
				if(!listaAuxiliar.get(11).equals("NULL")) { endereco.setNumero(cortarCasasDecimais((String)listaAuxiliar.get(11), false, false)); }
				if(!listaAuxiliar.get(12).equals("NULL")) { endereco.setBairro((String)listaAuxiliar.get(12)); }
				//if(!listaAuxiliar.get(13).equals("NULL")) { endereco.setCep(new Integer(cortarCasasDecimais((String)listaAuxiliar.get(13), true, false))); }
				if(cidade!=null && cidade.getPkCidade()!=null && cidade.getPkCidade()>0) { endereco.setCidade(cidade); }
				endereco.setStatus(ConstantesSisEducar.STATUS_ATIVO);
				
				//Monta o aluno
				aluno.setRa(cortarCasasDecimais(new Double((String)listaAuxiliar.get(5)).toString(), false, true)); //RA
				
				if(listaAuxiliar.get(6).equals("-1")) 		{ aluno.setRa2(""); }
				else if(listaAuxiliar.get(6).equals("-2"))  { aluno.setRa2("NULL"); }
				else 
				{ 
					stringAux = cortarCasasDecimais(new Double((String)listaAuxiliar.get(6)).toString(), false, true); //RA2
					stringAux = stringAux.substring(0, 1);
					aluno.setRa2(stringAux); //RA2
				}
				
				if(!listaAuxiliar.get(15).equals("NULL")) { aluno.setFolha(cortarCasasDecimais((String)listaAuxiliar.get(15), false, false)); } //Folha
				if(!listaAuxiliar.get(16).equals("NULL")) { aluno.setLivro(cortarCasasDecimais((String)listaAuxiliar.get(16), false, false)); }
				if(!listaAuxiliar.get(17).equals("NULL")) { aluno.setRegistro(new Integer(cortarCasasDecimais(new Double((String)listaAuxiliar.get(17)).toString(), false, false))); }
				if(!listaAuxiliar.get(19).equals("NULL")) { aluno.setLivroUF((String)listaAuxiliar.get(19)); }
				
				//Monta a pessoa
				pessoa.setNome((String)listaAuxiliar.get(2)); //Nome
				pessoa.setSexo((String)listaAuxiliar.get(3)); //Sexo
				pessoa.setDataNascimento((Date) ConversorUtils.convertStringToTimestamp(correcaoStringToStringDate((String)listaAuxiliar.get(4))));				
				//pessoa.setDataCadastro((Date) ConversorUtils.convertStringToTimestamp(new java.util.Date().toString("")));				
				
				//salva endereço 
				endereco = enderecoDAO.inserirEndereco(endereco);
				pessoa.setEndereco(endereco);
				pessoa.setUnidadeEscolar(unidadeEscolar);
				
				//salva pessoa
				pessoa = pessoaDAO.inserirPessoa(pessoa);
				
				aluno.setPessoa(pessoa);
				if(!listaAuxiliar.get(18).equals("NULL")) { cidadeNascimento.setPkCidade(cidadeDAO.obtemPKCidade(null, (String)listaAuxiliar.get(18))); }
				else {cidadeNascimento = null; }
				aluno.setCidadeNascimento(cidadeNascimento);
				
				aluno = alunoDAO.inserirAluno(aluno);
				
				countPosicao ++;
				if(aluno!=null && aluno.getPkAluno()>0 
						&& pessoa!=null && pessoa.getPkPessoa()>0
						&& endereco!=null && endereco.getPkEndereco()>0)
				{
						System.out.println(countPosicao);
				}
				else
				{
					System.out.println("A linha número " + countPosicao + " não deu certo");
				}
			}
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); }
		catch (IOException e) 			{ e.printStackTrace(); }
	}
	
	public static String cortarCasasDecimais(String numeroOrigem, Boolean validarCEP, Boolean apenasRetirarPonto)
	{
		Integer posicaoPonto = 0;
		String numeroDestino = "";
		String stringNova = "";
		
		if(numeroOrigem!=null && numeroOrigem.length() >0 && numeroOrigem.contains("."))
		{
			if(validarCEP)
			{
				Integer numeroDe0 = 0;
				Integer posicaoCortar = 0;
				numeroDestino = numeroOrigem.replace(".", "");
				numeroDe0 = numeroDestino.indexOf("E");
				posicaoCortar = numeroDestino.indexOf("E");
				stringNova = numeroDestino.substring(0, posicaoCortar);
				for (int i = 0; i < numeroDe0; i++) 
				{
					stringNova += "0";
				}
				return stringNova;
			}
			else if(apenasRetirarPonto)
			{
				stringNova = numeroOrigem.replace(".", "");
				return stringNova;
			}
			
			posicaoPonto = numeroOrigem.indexOf(".");
			numeroDestino = numeroOrigem.substring(0, posicaoPonto);
			return numeroDestino;
		}
		else
		{
			return numeroOrigem;
		}
	}
	
	public static String correcaoStringToStringDate(String original)
	{
		String ano = "";
		String mes = "";
		String dia = "";
		String aux = "";
		if(original!=null)
		{
			original = original.substring(0, original.length()-2);
			original = original.replace(".", "");
			
			if(original.length()==5){original += "000";}
			ano = original.substring(original.length()-4, original.length());
			mes = original.substring(original.length()-6, original.length()-4);
			dia = original.substring(0, original.length()-6);
			
			return dia + "/" + mes + "/" + ano;
		}
		else 
		{
			return original;
		}
	}
}