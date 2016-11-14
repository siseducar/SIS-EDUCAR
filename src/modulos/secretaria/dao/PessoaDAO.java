package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.EstadoCivil;
import modulos.secretaria.om.GrauInstrucao;
import modulos.secretaria.om.GrauParentesco;
import modulos.secretaria.om.Nacionalidade;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Raca;
import modulos.secretaria.om.Religiao;
import modulos.secretaria.om.SituacaoEconomica;
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
	
	/**
	 * Metodo para salvar os dados referente ao cadastro de uma pessoa
	 * @throws SQLException 
	 * 
	 * */
	public Pessoa salvarCadastroPessoa(Pessoa pessoaDados) throws SQLException {
		try {
			String querySQL;
			
			Integer numeroArgumentos = 1;
			
			querySQL = " INSERT INTO PESSOA ( ";
			querySQL += " NOME, CODIGO, ";
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				querySQL += " CPF, ";
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL += " RG, ";
			}
			if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0){
				querySQL += " CPFMAE, NOMEMAE, ";
			}
			if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0){
				querySQL += " CPFPAI, NOMEPAI, ";
			}
			if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0){
				querySQL += " CPFRESPONSAVEL, NOMERESPONSAVEL, FKGRAUPARENTESCO, ";
			}
			querySQL += " DATANASCIMENTO, SEXO, STATUS, FKRACA, ";
			querySQL += " FKSITUACAOECONOMICA, FKRELIGIAO, FKNACIONALIDADE, FKESTADOCIVIL, FKGRAUINSTRUCAO, ";
			querySQL += " FKENDERECO, FKNATURALIDADE, DATACADASTRO ) values ( ";
			
			querySQL += " ?, ?, ";
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {
				querySQL += " ?, ";
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL += " ?, ";
			}
			if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0){
				querySQL += " ?, ?, ";
			}
			if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0){
				querySQL += " ?, ?, ";
			}
			if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0){
				querySQL += " ?, ?, ?, ";
			}
			querySQL += " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE ) RETURNING PKPESSOA";
			
			ps = con.prepareStatement(querySQL);
			
			// NOME da Pessoa
			ps.setString(numeroArgumentos, pessoaDados.getNome());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, pessoaDados.getCodigo().toString());
			numeroArgumentos++;
			
			// CPF caso seja preenchido
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				ps.setLong(numeroArgumentos, pessoaDados.getCpf());
				numeroArgumentos++;
			} 

			// RG caso seja preenchido
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				ps.setString(numeroArgumentos, pessoaDados.getRg());
				numeroArgumentos++;
			}
			
			// CPF E NOME da mãe
			if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0){
				ps.setLong(numeroArgumentos, pessoaDados.getCpfMae());
				numeroArgumentos++;
				
				ps.setString(numeroArgumentos, pessoaDados.getNomeMae());
				numeroArgumentos++;
			}
			
			// CPF e NOME do pai
			if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0){
				ps.setLong(numeroArgumentos, pessoaDados.getCpfPai());
				numeroArgumentos++;
				
				ps.setString(numeroArgumentos, pessoaDados.getNomePai());
				numeroArgumentos++;
			}
			
			// CPF e NOME do responsavel
			if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0){
				ps.setLong(numeroArgumentos, pessoaDados.getCpfResponsavel());
				numeroArgumentos++;
				
				ps.setString(numeroArgumentos, pessoaDados.getNomeResponsavel());
				numeroArgumentos++;
				
				ps.setInt(numeroArgumentos, pessoaDados.getGrauParentesco().getPkGrauParentesco() );
				numeroArgumentos++;
			}
			
			// DATA de Nascimento
			ps.setDate(numeroArgumentos, pessoaDados.getDataNascimento());
			numeroArgumentos++;
			
			// SEXO da pessoa
			ps.setString(numeroArgumentos, pessoaDados.getSexo());
			numeroArgumentos++;
						
			// STATUS da pessoa
			ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
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
			
			//NACIONALIDADE da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getNacionalidade().getPkNacionalidade());
			numeroArgumentos++;
			
			// ESTADO CIVIL da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getEstadoCivil().getPkEstadoCivil());
			numeroArgumentos++;
			
			// GRAU DE INSTRUCAO da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getGrauInstrucao().getPkGrauInstrucao());
			numeroArgumentos++;

			// ENDERECO da pessoa
			ps.setInt(numeroArgumentos, pessoaDados.getEndereco().getPkEndereco());
			numeroArgumentos++;
			
			ps.setInt(numeroArgumentos, pessoaDados.getFkCidadeNascimento().getPkCidade());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				pessoaDados.setPkPessoa(rs.getInt("PKPESSOA"));
			}
			
			fecharConexaoBanco(con, ps, true, false);
			
			return pessoaDados;
		} catch(Exception e) {
			new EnderecoDAO().deletarEndereco(pessoaDados.getEndereco().getPkEndereco());
			new ContatoDAO().deletarContato(pessoaDados.getEndereco().getContato().getPkContato());
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
		String querySQL = "SELECT * FROM PESSOA"
				+ " WHERE STATUS = ?"
				+ " AND PKPESSOA = ?";
		
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
	 * Retorna uma lista de pessoas, ATENÇÂO: Os objetos Pessoa que estão dentro da lista está apenas com informações essenciais, nome e pk
	 * @author João Paulo
	 * @return List Pessoa
	 * @throws SQLException
	 */
	public List<Pessoa> obtemTodos()
	{
		try {
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
				pessoa.setPkPessoa(rs.getInt("PKPESSOA"));
				pessoa.setNome(rs.getString("NOME"));
				pessoa.setDataNascimento(rs.getDate("DATANASCIMENTO"));
				listaPessoas.add(pessoa);
			}
			return listaPessoas;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Pessoa inserirPessoa(Pessoa pessoa) throws SQLException 
	{
		Integer numeroArgumentos = 1;
		String querySQL = "INSERT INTO pessoa "
				+ " ( "
					+ " nome, nomefantasia, cpf, cnpj, semcpf, rg, datanascimento, datacadastro, sexo, "
					+ " tipoPessoa, falecido, datafalecimento, status, fkRaca, fkSituacaoEconomica, fkReligiao, "
					+ " fkTipoDeficiencia, fkNacionalidade, fkEstadoCivil, fkTurno, fkGrauInstrucao, fkEndereco"
				+ " ) "
				+ " values(?,?,?,?,?,?,?, now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
		ps.setObject(numeroArgumentos, pessoa.getTipoDeficiencia()!=null ? pessoa.getTipoDeficiencia().getPkTipoDeficiencia() : null);
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
	 * Retorna os dados completos do cadastro para alteração
	 * @author Michael
	 * @return Pessoa pessoaDados
	 */
	public Pessoa consultaCadastroPessoa(Integer pkPessoa) {
		
		try { 
			
			String querySQL = "SELECT "
					+ "	PS.*, "
					+ " CD.PKCIDADE, "
					+ " ES.PKESTADO "
					+ " FROM Pessoa PS "
					+ " INNER JOIN Cidade CD "
					+ " ON PS.FKNATURALIDADE = CD.PKCIDADE "
					+ " INNER JOIN Estado ES"
					+ " ON CD.FKESTADO = ES.PKESTADO "
					+ " WHERE PS.STATUS = ? AND PS.PKPESSOA = ? ";
			
			ps = con.prepareStatement(querySQL);
		
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			ps.setInt(2, pkPessoa);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				Pessoa pessoaDados = new Pessoa();
				Cidade municipioClienteDados = new Cidade();
				Endereco enderecoDados = new Endereco();
				Nacionalidade nacionalidadeDados = new Nacionalidade();
				Raca racaDados = new Raca();
				EstadoCivil estaCivilDados = new EstadoCivil();
				GrauInstrucao grauInstruDados = new GrauInstrucao();
				SituacaoEconomica situEconomicaDados = new SituacaoEconomica();
				Religiao religiaoDados = new Religiao();
				GrauParentesco grauParentescoDados = new GrauParentesco();
				Cidade cidadeNaturalidade = new Cidade(); 
				Estado estadoNaturalidade = new Estado();
				
				racaDados.setPkRaca(rs.getInt("FKRACA"));
				situEconomicaDados.setPkSituacaoEconomica(rs.getInt("FKSITUACAOECONOMICA"));
				religiaoDados.setPkReligiao(rs.getInt("FKRELIGIAO"));
				nacionalidadeDados.setPkNacionalidade(rs.getInt("FKNACIONALIDADE"));
				estaCivilDados.setPkEstadoCivil(rs.getInt("FKESTADOCIVIL"));
				grauInstruDados.setPkGrauInstrucao(rs.getInt("FKGRAUINSTRUCAO"));
				enderecoDados.setPkEndereco(rs.getInt("FKENDERECO"));
				grauParentescoDados.setPkGrauParentesco(rs.getInt("FKGRAUPARENTESCO"));
				cidadeNaturalidade.setPkCidade(rs.getInt("PKCIDADE"));
				estadoNaturalidade.setPkEstado(rs.getInt("PKESTADO"));
				pessoaDados.setPkPessoa(rs.getInt("PKPESSOA"));
				pessoaDados.setNome(rs.getString("NOME"));
				pessoaDados.setCpf(rs.getLong("CPF"));
				pessoaDados.setRg(rs.getString("RG"));
				pessoaDados.setDataNascimento(rs.getDate("DATANASCIMENTO"));
				pessoaDados.setSexo(rs.getString("SEXO"));
				pessoaDados.setNomeMae(rs.getString("NOMEMAE"));
				pessoaDados.setCpfMae(rs.getLong("CPFMAE"));
				pessoaDados.setNomePai(rs.getString("NOMEPAI"));
				pessoaDados.setCpfPai(rs.getLong("CPFPAI"));
				pessoaDados.setNomeResponsavel(rs.getString("NOMERESPONSAVEL"));
				pessoaDados.setCpfResponsavel(rs.getLong("CPFRESPONSAVEL"));
				
				pessoaDados.setRaca(racaDados);
				pessoaDados.setSituacaoEconomica(situEconomicaDados);
				pessoaDados.setReligiao(religiaoDados);
				pessoaDados.setNacionalidade(nacionalidadeDados);
				pessoaDados.setEstadoCivil(estaCivilDados);
				pessoaDados.setGrauInstrucao(grauInstruDados);
				pessoaDados.setEndereco(enderecoDados);
				pessoaDados.setFkMunicipioCliente(municipioClienteDados);
				pessoaDados.setGrauParentesco(grauParentescoDados);
				
				cidadeNaturalidade.setEstado(estadoNaturalidade);
				pessoaDados.setFkCidadeNascimento(cidadeNaturalidade);

				return pessoaDados;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	/**
	 * Retorna os dados (Nome) do responsavel
	 * @author Michael
	 * @return String nomePessoa
	 */
	public String consultaNomeResponsavel(Long cpf, String sexo){
		try {
			
			String nomePessoa = null;
			String querySQL = "SELECT NOME FROM Pessoa WHERE CPF = ? ";
			
			if(sexo != null && !sexo.equals("")) {
				querySQL+= " AND SEXO = ?";
			}
			
			ps = con.prepareStatement(querySQL);
			
			ps.setString(1, cpf.toString());
			if(sexo != null && !sexo.equals("")) {
				ps.setString(2, sexo);
			}
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				nomePessoa = rs.getString("NOME");
			}
			
			return nomePessoa;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Retorna uma lista de Responsaveis da pessoa menor de idade
	 * @author Michael
	 * @return Lista Pessoa
	 */
	public List<Pessoa> consultaCadastroPessoa(Pessoa pessoaDados) {
		try {
			Integer numeroArgumentos = 1;
			String formataNome = "%"+ pessoaDados.getNome() + "%";
			
			List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
			
			String querySQL = " SELECT * FROM PESSOA "
					+ " WHERE STATUS = ? ";
				
				if( pessoaDados.getNome() != null && !pessoaDados.getNome().equals("")) {
					querySQL+= " AND NOME ILIKE ?";
				}
				if( pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0) {
					querySQL+= " AND CPF = ? ";
				}
				if( pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {
					querySQL+= " AND RG = ? ";
				}
				if(pessoaDados.getDataNascimento() != null) {
					querySQL+= " AND DATANASCIMENTO = ? "; 
				}
				if(pessoaDados.getSexo() != null && !pessoaDados.getSexo().equals("")) {
					querySQL+= " AND SEXO = ?";
				}
				querySQL+= " ORDER BY NOME";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArgumentos++;
			
			if( pessoaDados.getNome() != null && !pessoaDados.getNome().equals("")) {
				ps.setString(numeroArgumentos, formataNome);
				numeroArgumentos++;
			}
			if( pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0) {
				ps.setLong(numeroArgumentos, pessoaDados.getCpf());
				numeroArgumentos++;
			}
			if( pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {
				ps.setString(numeroArgumentos, pessoaDados.getRg());
				numeroArgumentos++;
			}
			if(pessoaDados.getDataNascimento() != null) {
				ps.setDate(numeroArgumentos, pessoaDados.getDataNascimento());
				numeroArgumentos++;
			}
			if(pessoaDados.getSexo() != null && !pessoaDados.getSexo().equals("")) {
				ps.setString(numeroArgumentos, pessoaDados.getSexo());
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Pessoa dadosPessoa = new Pessoa();
				
				dadosPessoa.setPkPessoa(rs.getInt("PKPESSOA"));
				dadosPessoa.setNome(rs.getString("NOME"));
				dadosPessoa.setDataNascimento(rs.getDate("DATANASCIMENTO"));
				
				listaPessoa.add(dadosPessoa);
			}
			
			return listaPessoa;
		} catch (Exception e)  {			
			return null;
		}
	}
	
	/**
	 * Delete de um cadastro, atualiza o status do banco para desabilitado
	 * @author Michael
	 * @return Boolean
	 */
	public Boolean deletarPessoa(Integer pkPessoa) {
		try {
			String querySQL = "UPDATE PESSOA "
					+ " SET STATUS = ?"
					+ " WHERE PKPESSOA = ? RETURNING STATUS";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, pkPessoa);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) {
					fecharConexaoBanco(con, ps, true, false);
					
					return true;
				}
			}
			fecharConexaoBanco(con, ps, false, true);
			fecharConexaoBanco(con, ps, true, false);
			
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Consulta os dados de menor para valiadr se ja existe cadastro
	 * @author Michael
	 * @return Boolean
	 */
	public Boolean validaMenorIdade(Pessoa pessoaDados){
		try {
			String querySQL = " SELECT * FROM PESSOA "
					+ " WHERE STATUS = ? "
					+ " AND DATANASCIMENTO = ? "
					+ " AND SEXO = ? "
					+ " ";
			if(pessoaDados.getCpfMae() != null && pessoaDados.getCpfMae() != 0 ) {
				querySQL+= " AND CPFMAE = ?";
			}
			if(pessoaDados.getCpfPai() != null && pessoaDados.getCpfPai() != 0 ) {
				querySQL+= " AND CPFPAI = ?";
			}
			if(pessoaDados.getCpfResponsavel() != null && pessoaDados.getCpfResponsavel() != 0 ) {
				querySQL+= " AND CPFRESPONSAVEL = ?";
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
