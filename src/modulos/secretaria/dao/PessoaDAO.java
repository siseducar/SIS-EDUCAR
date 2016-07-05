package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
	public Boolean salvarCadastroPessoa(Pessoa pessoaDados) throws SQLException{
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
			querySQL += " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE )";
			
			ps = con.prepareStatement(querySQL.toString());
			
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
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, pessoaDados.getEmail());
			
			fecharConexaoBanco(con, ps, false, true);
			
			return true;
		} catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					e.toString(),null));
			return false;
		}
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
}
