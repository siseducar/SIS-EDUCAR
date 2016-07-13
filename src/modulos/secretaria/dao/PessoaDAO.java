package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Pessoa;
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
	
	public PessoaDAO() throws SQLException {
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Metodo para salvar os dados referente ao cadastro de uma pessoa
	 * 
	 * */
	public Pessoa salvarCadastroPessoa(Pessoa pessoaDados) {
		try {
			String querySQL;
			
			Integer numeroArgumentos = 1;
			
			querySQL = " INSERT INTO PESSOA ( ";
			querySQL += " NOME, ";
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				querySQL += " CPF, ";
				querySQL += " SEMCPF, ";
			} else {
				querySQL += " SEMCPF, ";
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL += " RG, ";
			}
			querySQL += " DATANASCIMENTO,  SEXO, TELEFONERESIDENCIAL, TELEFONECELULAR, TIPOPESSOA, STATUS, FKRACA, ";
			querySQL += " FKSITUACAOECONOMICA, FKRELIGIAO, FKREGIAO, FKNACIONALIDADE, FKESTADOCIVIL, FKGRAUINSTRUCAO, ";
			querySQL += " FKMUNICIPIOCLIENTE, EMAIL, DATACADASTRO ) values ( ";
			
			querySQL += " ?, ";
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {
				querySQL += " ?, ?, ";
			} else {
				querySQL += " ?, ";
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL += " ?, ";
			}
			querySQL += " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE ) RETURNING PKPESSOA";
			
			ps = con.prepareStatement(querySQL);
			
			// NOME da Pessoa
			ps.setString(numeroArgumentos, pessoaDados.getNome());
			numeroArgumentos++;
			
			// CPF caso seja preenchido
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				ps.setLong(numeroArgumentos, pessoaDados.getCpf());
				numeroArgumentos++;
				ps.setBoolean(numeroArgumentos, false);
				numeroArgumentos++;
			} else {
				ps.setBoolean(numeroArgumentos, true);
				numeroArgumentos++;
			}
			// RG caso seja preenchido
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				ps.setString(numeroArgumentos, pessoaDados.getRg());
				numeroArgumentos++;
			}
			
			// DATA de Nascimento
			ps.setDate(numeroArgumentos, pessoaDados.getDataNascimento());
			numeroArgumentos++;
			
			// SEXO da pessoa
			ps.setString(numeroArgumentos, pessoaDados.getSexo());
			numeroArgumentos++;
			
			// TIPO de pessoa a ser cadastrada
			ps.setInt(numeroArgumentos, pessoaDados.getTipoPessoa());
			numeroArgumentos++;
			
			// STATUS da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getStatus());
			numeroArgumentos++;
			
			// RACA da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getRaca().getPkRaca());
			numeroArgumentos++;
			
			// SITUACAO ECONOMICA da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getSituacaoEconomica().getPkSituacaoEconomica());
			numeroArgumentos++;
			
			// RELIGIAO da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getReligiao().getPkReligiao());
			numeroArgumentos++;
			
			// REGIAO da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getRegiao().getPkRegiao());
			numeroArgumentos++;
			
			//NACIONALIDADE da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getNacionalidade().getPkNacionalidade());
			numeroArgumentos++;
			
			// ESTADO CIVIL da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getEstadoCivil().getPkEstadoCivil());
			numeroArgumentos++;
			
			// GRAU DE INSTRUCAO da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getGrauInstrucao().getPkGrauInstrucao());
			numeroArgumentos++;
			
			// CODIGO DO MUNICIPIO do cliente
			ps.setInt(numeroArgumentos, pessoaDados.getFkMunicipioCliente().getPkCidade());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				pessoaDados.setPkPessoa(rs.getInt("PKPESSOA"));
			}
			
			return pessoaDados;
		} catch(Exception e) {
			return null;
		}
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
	public Pessoa obtemUnicoPessoaSimples(String cpf) throws SQLException {
		Pessoa pessoa = null;
		String querySQL = "SELECT * FROM PESSOA"
				+ " WHERE STATUS = ? AND CPF = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setString(2, cpf);
		
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			pessoa = new Pessoa();
			pessoa.setPkPessoa(rs.getInt("pkPessoa"));
			pessoa.setNome(rs.getString("nome"));
			pessoa.setCpf(rs.getLong("cpf"));
		}
		
		return pessoa;
	}
	
	/**
	 * Metodo para buscar a PK da pessoa cadastrada
	 * 
	 * *
	 */
	public Integer consultaCadastro(Pessoa pessoaDados) {
		try {
			Integer numeroArgumentos = 1;
			String querySQL = "SELECT PKPESSOA FROM PESSOA ";
				querySQL += " WHERE ";
				querySQL += " NOME = ? ";
				querySQL += " AND STATUS = ? ";
				if( pessoaDados.getCpf() != null && pessoaDados.getCpf() > 0 ) {
					querySQL += "AND  CPF = ? ";
				}
				if( pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {
					querySQL += " AND RG = ? ";
				}
				querySQL += " AND DATANASCIMENTO = ? ";
				querySQL += " AND SEXO = ? ";
				
				if( pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() > 0 ) {
					querySQL += " AND CPFMAE = ? ";
				}
				if( pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() > 0 ) {
					querySQL += " AND CPFPAI = ? ";
				}
				if( pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() > 0 ) {
					querySQL += " AND CPFRESPONSAVEL = ? ";
				}
			
				ps = con.prepareStatement(querySQL);
			
			ps.setString(numeroArgumentos, pessoaDados.getNome());
			numeroArgumentos++;
			
			ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArgumentos++;
			
			if( pessoaDados.getCpf() != null && pessoaDados.getCpf() > 0 ) {
				ps.setString(numeroArgumentos, pessoaDados.getCpf().toString());
				numeroArgumentos++;
			}
			if( pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {
				ps.setString(numeroArgumentos, pessoaDados.getRg());
				numeroArgumentos++;
			}
			ps.setDate(numeroArgumentos, pessoaDados.getDataNascimento());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, pessoaDados.getSexo());
			numeroArgumentos++;
			
			if( pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() > 0 ) {
				ps.setString(numeroArgumentos, pessoaDados.getCpfMae().toString());
				numeroArgumentos++;
			}
			if( pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() > 0 ) {
				ps.setString(numeroArgumentos, pessoaDados.getCpfPai().toString());
				numeroArgumentos++;
			}
			if( pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() > 0 ) {
				ps.setString(numeroArgumentos, pessoaDados.getCpfResponsavel().toString());
				numeroArgumentos++;
			}
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				pessoaDados.setPkPessoa(rs.getInt("PKPESSOA"));
			}
			
			return pessoaDados.getPkPessoa();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
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
		ps.setDate(numeroArgumentos, pessoa.getDataNascimento());
		numeroArgumentos++;
		ps.setString(numeroArgumentos, pessoa.getSexo());
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
}
