package modulos.RH.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.Pessoa;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PessoaDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public PessoaDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	public Pessoa inserirPessoa(Pessoa pessoa) throws SQLException 
	{
		Integer numeroArgumentos = 1;
		String querySQL = "INSERT INTO pessoa "
				+ " ("
					+ " nome, nomefantasia, cpf, cnpj, semcpf, rg, datanascimento, datacadastro, sexo, telefonecomercial, "
					+ " telefoneresidencial, telefonecelular, tipoPessoa, falecido, datafalecimento, status, fkRaca, fkSituacaoEconomica, fkReligiao, "
					+ " fkTipoDeficiencia, fkRegiao, fkNacionalidade, fkEstadoCivil, fkTurno, fkGrauInstrucao, fkUnidadeEscolar, fkEndereco"
				+ ") "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(numeroArgumentos, pessoa.getNome());
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getNomeFantasia());
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getCpf());
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getCnpj());
		numeroArgumentos++;
		ps.setBoolean(numeroArgumentos, pessoa.getSemCpf() !=null ? pessoa.getSemCpf() : false);
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getRg());
		numeroArgumentos++;
		ps.setDate(numeroArgumentos, new Date(0));
		numeroArgumentos++;
		ps.setDate(numeroArgumentos, new Date(0));
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getSexo());
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, pessoa.getTelefoneComercial());
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, pessoa.getTelefoneResidencial());
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, pessoa.getTelefoneCelular());
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, pessoa.getTipoPessoa());
		numeroArgumentos++;
		ps.setBoolean(numeroArgumentos, pessoa.getFalecido()!=null ? pessoa.getFalecido() : false);
		numeroArgumentos++;
		ps.setDate(numeroArgumentos, pessoa.getDataFalecimento());
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, null);
		if(pessoa.getRaca()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getRaca().getPkRaca());
			numeroArgumentos++;
		}
		
		if(pessoa.getSituacaoEconomica()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getSituacaoEconomica().getPkSituacaoEconomica());
			numeroArgumentos++;
		}
		
		if(pessoa.getReligiao()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getReligiao().getPkReligiao());
			numeroArgumentos++;
		}
		
		if(pessoa.getTipoDeficiencia()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getTipoDeficiencia().getPkTipoDeficiencia());
			numeroArgumentos++;
		}
		
		if(pessoa.getRegiao()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getRegiao().getPkRegiao());
			numeroArgumentos++;
		}
		
		if(pessoa.getNacionalidade()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getNacionalidade().getPkNacionalidade());
			numeroArgumentos++;
		}
		
		if(pessoa.getEstadoCivil()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getEstadoCivil().getPkEstadoCivil());
			numeroArgumentos++;
		}
		
		if(pessoa.getTurno()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getTurno().getPkRaca());
			numeroArgumentos++;
		}
		
		if(pessoa.getGrauInstrucao()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getGrauInstrucao().getPkGrauInstrucao());
			numeroArgumentos++;
		}
		
		if(pessoa.getUnidadeEscolar()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getUnidadeEscolar().getPkUnidadeEscolar());
			numeroArgumentos++;
		}
		
		if(pessoa.getEndereco()!=null)
		{
			ps.setInt(numeroArgumentos, pessoa.getEndereco().getPkEndereco());
			numeroArgumentos++;
		}
		
		fecharConexaoBanco(con, ps, false, true);
		
		pessoa.setPkPessoa(obtemPKPessoa(pessoa.getNome(), pessoa.getNomeFantasia(), pessoa.getCpf(), pessoa.getCnpj()));
		
		return pessoa;
	}
	
	/**
	 * Busca pk da pessoa salva
	 * @author João Paulo
	 * @param nome
	 * @param nomeFantasia
	 * @param cpf
	 * @param cnpj
	 * @return Integer pkPessoa
	 * @throws SQLException
	 */
	public Integer obtemPKPessoa(String nome, String nomeFantasia, String cpf, String cnpj) throws SQLException
	{
		Integer numeroArgumentos = 1;
		
		String querySQL = "SELECT * FROM pessoa"
				+ " WHERE status = ?";
		
		if(nome!=null && nome.length() >0) 					{ querySQL += " AND nome = ?"; }
		if(nomeFantasia!=null && nomeFantasia.length() >0)	{ querySQL += " AND nomeFantasia = ?"; }
		if(cpf!=null && cpf.length() >0)		 			{ querySQL += " AND cpf = ?"; }
		if(cnpj!=null && cnpj.length() >0)					{ querySQL += " AND cnpj = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		if(nome!=null && nome.length() >0) 
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, nome);
		}
		
		if(nomeFantasia!=null && nomeFantasia.length()>0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, nomeFantasia);
		}
		
		if(cpf!=null && cpf.length()>0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, cpf);
		}
		
		if(cnpj!=null && cnpj.length() >0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, cnpj);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkPessoa");
		}
		
		return null;
	}
}
