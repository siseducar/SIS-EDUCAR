package modulos.RH.dao;

import java.sql.Connection;
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
	
	public Pessoa inserirPessoa(Pessoa pessoa) throws SQLException 
	{
		String querySQL = "INSERT INTO pessoa "
				+ " (nome, nomefantasia, cpf, cnpj, semcpf, rg, datanascimento, datacadastro, sexo, telefonecomercial, "
				+ " telefoneresidencial, telefonecelular,"
				+ " tipoPessoa, falecido, datafalecimento, status, fkRaca, fkSituacaoEconomica, fkReligiao, "
				+ " fkTipoDeficiencia, fkRegiao, fkNacionalidade, fkEstadoCivil, fkTurno, "
				+ " fkGrauInstrucao, fkUnidadeEscolar, fkEndereco) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(1, pessoa.getNome());
		ps.setString(2, pessoa.getNomeFantasia());
		ps.setString(3, pessoa.getCpf());
		ps.setString(4, pessoa.getCnpj());
		ps.setBoolean(5, pessoa.getSemCpf());
		ps.setDate(6, pessoa.getDataNascimento());
		ps.setDate(7, pessoa.getDataCadastro());
		ps.setString(8, pessoa.getSexo());
		ps.setInt(9, pessoa.getTelefoneComercial());
		ps.setInt(10, pessoa.getTelefoneResidencial());
		ps.setInt(11, pessoa.getTelefoneCelular());
		ps.setInt(12, pessoa.getTipoPessoa());
		ps.setBoolean(13, pessoa.getFalecido());
		ps.setDate(14, pessoa.getDataFalecimento());
		ps.setInt(15, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(16, pessoa.getRaca()!=null ? pessoa.getRaca().getPkRaca() : null);
		ps.setInt(17, pessoa.getSituacaoEconomica()!=null ? pessoa.getSituacaoEconomica().getPkSituacaoEconomica() : null);
		ps.setInt(18, pessoa.getReligiao()!=null ? pessoa.getReligiao().getPkReligiao() : null);
		ps.setInt(19, pessoa.getTipoDeficiencia()!=null ? pessoa.getTipoDeficiencia().getPkTipoDeficiencia() : null);
		ps.setInt(20, pessoa.getRegiao()!=null ? pessoa.getRegiao().getPkRegiao() : null);
		ps.setInt(21, pessoa.getNacionalidade()!=null ? pessoa.getNacionalidade().getPkNacionalidade() : null);
		ps.setInt(22, pessoa.getEstadoCivil()!=null ? pessoa.getEstadoCivil().getPkEstadoCivil() : null);
		ps.setInt(23, pessoa.getTurno()!=null ? pessoa.getTurno().getPkRaca() : null);
		ps.setInt(24, pessoa.getGrauInstrucao()!=null ? pessoa.getGrauInstrucao().getPkGrauInstrucao() : null);
		ps.setInt(25, pessoa.getUnidadeEscolar()!=null ? pessoa.getUnidadeEscolar().getPkUnidadeEscolar() : null);
		ps.setInt(26, pessoa.getEndereco()!=null ? pessoa.getEndereco().getPkEndereco() : null);
		
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
