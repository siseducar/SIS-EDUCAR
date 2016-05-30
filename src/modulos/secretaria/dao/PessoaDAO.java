package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Raca;
import sisEdcuar.conexaoBanco.ConectaBanco;
import sisEdcuar.dao.SisEducarDAO;
import sisEdcuar.utils.ConstantesSisEducar;

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
				+ " ( "
					+ " nome, nomefantasia, cpf, cnpj, semcpf, rg, datanascimento, datacadastro, sexo, telefonecomercial, "
					+ " telefoneresidencial, telefonecelular, tipoPessoa, falecido, datafalecimento, status, fkRaca, fkSituacaoEconomica, fkReligiao, "
					+ " fkTipoDeficiencia, fkRegiao, fkNacionalidade, fkEstadoCivil, fkTurno, fkGrauInstrucao, fkUnidadeEscolar, fkEndereco"
				+ " ) "
				+ " values(?,?,?,?,?,?,?, now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		ps = con.prepareStatement(querySQL);
		
		ps.setString(numeroArgumentos, pessoa.getNome());
		/*numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getNomeFantasia());
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getCpf());
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getCnpj());
		numeroArgumentos++;
		ps.setBoolean(numeroArgumentos, pessoa.getSemCpf() !=null ? pessoa.getSemCpf() : false);
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getRg());
		numeroArgumentos++;*/
		ps.setDate(numeroArgumentos, pessoa.getDataNascimento()!=null ? pessoa.getDataNascimento() : new Date(0));
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getSexo());
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, Integer.valueOf(pessoa.getTelefoneComercial()));
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, Integer.valueOf(pessoa.getTelefoneResidencial()));
		numeroArgumentos++;
		ps.setInt(numeroArgumentos, Integer.valueOf(pessoa.getTelefoneCelular()));
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
		ps.setObject(numeroArgumentos, pessoa.getTurno()!=null ? pessoa.getTurno().getPkTurno() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getGrauInstrucao()!=null ? pessoa.getGrauInstrucao().getPkGrauInstrucao() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getUnidadeEscolar()!=null ? pessoa.getUnidadeEscolar().getPkUnidadeEscolar() : null);
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, pessoa.getEndereco()!=null ? pessoa.getEndereco().getPkEndereco() : null);
		
		fecharConexaoBanco(con, ps, false, true);
		
		//pessoa.setPkPessoa(obtemPKPessoa(pessoa.getNome(), pessoa.getNomeFantasia(), pessoa.getCpf(), pessoa.getCnpj()));
		
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
	
	/*
	 * Metodo utilizado para salvar os dados da pessoa
	 */
	public Pessoa salvarCadastroPessoa(Pessoa pessoaDados) throws SQLException{
		try {
			StringBuilder querySQL = new StringBuilder();
			
			querySQL.append(" INSERT INTO PESSOA ( ");
			querySQL.append(" NOME, ");
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				querySQL.append(" CPF, ");
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL.append(" RG, ");
			}
			querySQL.append(" DATANASCIMENTO, ");
			querySQL.append(" SEXO, ");
			querySQL.append(" TELEFONERESIDENCIAL, ");
			querySQL.append(" TELEFONECELULAR, ");
			querySQL.append(" TIPOPESSOA, ");
			querySQL.append(" STATUS, ");
			querySQL.append(" FKRACA, ");
			querySQL.append(" FKSITUACAOECONOMICA, ");
			querySQL.append(" FKRELIGIAO, ");
			querySQL.append(" FKREGIAO, ");
			querySQL.append(" FKNACIONALIDADE, ");
			querySQL.append(" FKESTADOCIVIL, ");
			querySQL.append(" FKGRAUINSTRUCAO, ");
			querySQL.append(" FKMUNICIPIOCLIENTE ");
			
			if(pessoaDados.getNome() != null && pessoaDados.getCpfMae() != null) {
				querySQL.append(" ,NOMEMAE, ");
				querySQL.append(" CPFMAE, ");
			}
			if(pessoaDados.getNomePai() != null && pessoaDados.getCpfPai() != null) {
				querySQL.append(" NOMEPAI, ");
				querySQL.append(" CPFPAI, ");
			}
			if(pessoaDados.getNomeResponsavel() != null && pessoaDados.getCpfResponsavel() != null) {
				querySQL.append(" NOMERESPONSAVEL, ");
				querySQL.append(" CPFRESPONSAVEL ");
			}
			
			querySQL.append(" ) values ( ");
			
			ps = con.prepareStatement(querySQL.toString());
			
			ps.setString(1, pessoaDados.getNome());
			ps.setLong(2, pessoaDados.getCpf());
			ps.setString(3, pessoaDados.getRg());
			ps.setDate(4, pessoaDados.getDataNascimento());
			ps.setDate(5, pessoaDados.getDataCadastro());
			ps.setString(6, pessoaDados.getSexo());
			ps.setLong(7, Long.parseLong(pessoaDados.getTelefoneResidencial()));
			ps.setLong(8, Long.parseLong(pessoaDados.getTelefoneCelular()));
			ps.setInt(9, pessoaDados.getTipoPessoa());
			ps.setInt(10, pessoaDados.getStatus());
			ps.setInt(11, pessoaDados.getRaca().getPkRaca());
			ps.setInt(12, pessoaDados.getSituacaoEconomica().getPkSituacaoEconomica());
			ps.setInt(13, pessoaDados.getReligiao().getPkReligiao());
			ps.setInt(14, pessoaDados.getRegiao().getPkRegiao());
			ps.setInt(15, pessoaDados.getNacionalidade().getPkNacionalidade());
			ps.setInt(16, pessoaDados.getEstadoCivil().getPkEstadoCivil());
			ps.setInt(17, pessoaDados.getGrauInstrucao().getPkGrauInstrucao());
			ps.setObject(18, pessoaDados.getFkMunicipioCliente()!=null ? pessoaDados.getFkMunicipioCliente().getPkCidade() : null);
			
			fecharConexaoBanco(con, ps, false, true);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retorna uma lista de pessoas, ATENÇÂO: Os objetos Pessoa que estão dentro da lista está apenas com informações essenciais, nome e pk
	 * @author João Paulo
	 * @return List Pessoa
	 * @throws SQLException
	 */
	public List<Pessoa> obtemTodos() throws SQLException
	{
		Pessoa pessoa = null;
		List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
		String querySQL = "SELECT * FROM pessoa"
				+ " WHERE status = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			pessoa = new Pessoa();
			pessoa.setPkPessoa(rs.getInt("pkPessoa"));
			pessoa.setNome(rs.getString("nome"));
			listaPessoas.add(pessoa);
		}
		return listaPessoas;
	}
	
	/**
	 * Obtem a pessoa pela PK da mesma com apenas algumas informações dessa pessoa
	 * @author João Paulo
	 * @return
	 * @throws SQLException
	 */
	public Pessoa obtemPessoaSimples(Integer pkPessoa) throws SQLException
	{
		Pessoa pessoa = null;
		String querySQL = "SELECT * FROM pessoa"
				+ " WHERE status = ?"
				+ " AND pkPessoa = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkPessoa);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			pessoa = new Pessoa();
			pessoa.setPkPessoa(rs.getInt("pkPessoa"));
			pessoa.setNome(rs.getString("nome"));
			pessoa.setCpf(rs.getLong("cpf"));
		}
		return pessoa;
	}
	
	/**
	 * Retorna a pessoa com apenas as informações simples da mesma
	 * @author João Paulo
	 * @param cpf
	 * @return Pessoa
	 * @throws SQLException
	 */
	public Pessoa obtemUnicoPessoaSimples(String cpf) throws SQLException
	{
		Pessoa pessoa = null;
		String querySQL = "SELECT * FROM pessoa"
				+ " WHERE status = ? AND cpf = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setString(2, cpf);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			pessoa = new Pessoa();
			pessoa.setPkPessoa(rs.getInt("pkPessoa"));
			pessoa.setNome(rs.getString("nome"));
			pessoa.setCpf(rs.getLong("cpf"));
		}
		
		return pessoa;
	}
}
