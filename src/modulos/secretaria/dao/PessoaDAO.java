package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.secretaria.om.Pessoa;
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
	
	public PessoaDAO() throws SQLException {
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Metodo para salvar os dados referente ao cadastro de uma pessoa
	 * 
	 * */
	public Pessoa salvarCadastroPessoa(Pessoa pessoaDados) throws SQLException{
		try {
			String querySQL;
			
			Integer numeroArgumentos = 1;
			
			querySQL = " INSERT INTO PESSOA ( ";
			querySQL += " NOME, ";
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				querySQL += " CPF, ";
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL += " RG, ";
			}
			querySQL += " DATANASCIMENTO,  SEXO, TELEFONERESIDENCIAL, TELEFONECELULAR, TIPOPESSOA, STATUS, FKRACA, ";
			querySQL += " FKSITUACAOECONOMICA, FKRELIGIAO, FKREGIAO, FKNACIONALIDADE, FKESTADOCIVIL, FKGRAUINSTRUCAO, ";
			querySQL += " FKMUNICIPIOCLIENTE ) values ( ";
			
			querySQL += " ?, ";
			if(pessoaDados.getCpf() != null && pessoaDados.getCpf() != 0 ) {				
				querySQL += " ?, ";
			}
			if(pessoaDados.getRg() != null && !pessoaDados.getRg().equals("")) {				
				querySQL += " ?, ";
			}
			querySQL += " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			
			ps = con.prepareStatement(querySQL.toString());
			
			// NOME da Pessoa
			ps.setString(numeroArgumentos, pessoaDados.getNome());
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
			
			// DATA de Nascimento
			ps.setDate(numeroArgumentos, pessoaDados.getDataNascimento());
			numeroArgumentos++;
			
			// SEXO da pessoa
			ps.setString(numeroArgumentos, pessoaDados.getSexo());
			numeroArgumentos++;
			
			// TEL RESIDENCIAL para contato
			ps.setLong(numeroArgumentos, Long.parseLong(pessoaDados.getTelefoneResidencial()));
			numeroArgumentos++;
			
			// TEL CELULAR para contato
			ps.setLong(numeroArgumentos, Long.parseLong(pessoaDados.getTelefoneCelular()));
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
			
			fecharConexaoBanco(con, ps, false, true);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
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
	 * Metodo para buscar o CODIGO da pessoa cadastrada
	 * 
	 * *
	 */
}
