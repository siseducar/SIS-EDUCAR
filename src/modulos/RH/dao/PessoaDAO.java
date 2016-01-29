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
		ps.setObject(numeroArgumentos, pessoa.getRaca()!=null ? pessoa.getRaca().getPkRaca() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getSituacaoEconomica()!=null ? pessoa.getSituacaoEconomica().getPkSituacaoEconomica() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getReligiao()!=null ? pessoa.getRegiao().getPkRegiao() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getTipoDeficiencia()!=null ? pessoa.getTipoDeficiencia().getPkTipoDeficiencia() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getRegiao()!=null ? pessoa.getRegiao().getPkRegiao() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getNacionalidade()!=null ? pessoa.getNacionalidade().getPkNacionalidade() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getEstadoCivil()!=null ? pessoa.getEstadoCivil().getPkEstadoCivil() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getTurno()!=null ? pessoa.getTurno().getPkRaca() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getGrauInstrucao()!=null ? pessoa.getGrauInstrucao().getPkGrauInstrucao() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getUnidadeEscolar()!=null ? pessoa.getUnidadeEscolar().getPkUnidadeEscolar() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getEndereco()!=null ? pessoa.getEndereco().getPkEndereco() : null);
		
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
