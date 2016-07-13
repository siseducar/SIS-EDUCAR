package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Endereco;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class EnderecoDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public EnderecoDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Insere o endereço e já obtem a pk do enreço salvo e retorna o objeto completo
	 * @author João Paulo
	 * @param endereco
	 * @return Endereco
	 */
	public Endereco inserirEndereco(Endereco endereco)
	{
		try 
		{
			String querySQL = "INSERT INTO endereco "
					+ " (cep, logradouro, bairro, numero, complemento, tipo, status, fkcidade, fkMunicipioCliente, fkContato) values(?,?,?,?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setObject(1, endereco.getCep());
			ps.setObject(2, endereco.getLogradouro());
			ps.setObject(3, endereco.getBairro());
			ps.setObject(4, endereco.getNumero());
			ps.setObject(5, endereco.getComplemento());
			ps.setInt(6, 0);
			ps.setInt(7, ConstantesSisEducar.STATUS_ATIVO);
			
			if(endereco!=null && endereco.getCidade()!=null && endereco.getCidade().getPkCidade()>0) { ps.setInt(8, endereco.getCidade().getPkCidade()); }
			else { ps.setObject(8, null); }
			
			if(endereco!=null && endereco.getFkMunicipioCliente()!=null && endereco.getFkMunicipioCliente().getPkCidade()>0) { ps.setInt(9, endereco.getFkMunicipioCliente().getPkCidade()); }
			else { ps.setObject(9, null);}
			
			if(endereco!=null && endereco.getContato()!=null && endereco.getContato().getPkContato()>0) { ps.setInt(10, endereco.getContato().getPkContato()); }
			else { ps.setObject(10, null);}
			
			fecharConexaoBanco(con, ps, false, true);

			endereco.setPkEndereco(obtemPKEndereco(endereco.getCep(), endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero(), endereco.getComplemento(), endereco.getTipo(), 
					endereco.getCidade()));
			
			return endereco;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	/**
	 * Atualiza as informações do endereço a partir de sua PK
	 * @author João Paulo
	 * @param endereco
	 * @return Endereco
	 * @throws SQLException
	 */
	public Endereco atualizarEndereco(Endereco endereco) throws SQLException
	{
		String querySQL = "UPDATE endereco "
				+ " SET (cep, logradouro, bairro, numero, complemento, tipo, fkcidade) = "
				+ " (?,?,?,?,?,?,?)"
				+ " WHERE pkEndereco = ?";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setObject(1, endereco.getCep());
		ps.setObject(2, endereco.getLogradouro());
		ps.setObject(3, endereco.getBairro());
		ps.setObject(4, endereco.getNumero());
		ps.setObject(5, endereco.getComplemento());
		ps.setObject(6, endereco.getTipo());
		ps.setObject(7, endereco.getCidade()!=null ? endereco.getCidade().getPkCidade() : null);
		ps.setObject(8, endereco.getPkEndereco());
		
		fecharConexaoBanco(con, ps, false, true);
		
		return endereco;
	}
	
	/**
	 * Busca apenas a pk do endereço a partir dos parâmetros recebidos
	 * @author João Paulo
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param numero
	 * @param complemento
	 * @param tipo
	 * @param fkCidade
	 * @return Integer pkEndereco
	 * @throws SQLException
	 */
	public Integer obtemPKEndereco(Integer cep, String logradouro, String bairro, String numero, String complemento, String tipo, Cidade cidade) throws SQLException
	{
		Integer numeroArgumentos = 1;
		
		String querySQL = "SELECT * FROM endereco"
				+ " WHERE status = ?";
		
		if(cep!=null && cep >0) 						{ querySQL += " AND cep = ?"; }
		if(logradouro!=null && logradouro.length() >0)	{ querySQL += " AND logradouro = ?"; }
		if(bairro!=null && bairro.length() >0)		 	{ querySQL += " AND bairro = ?"; }
		if(numero!=null && numero.length() >0)			{ querySQL += " AND numero = ?"; }
		if(complemento!=null && complemento.length() >0){ querySQL += " AND complemento = ?"; }
		if(tipo!=null && tipo.length() >0)	 			{ querySQL += " AND tipo = ?"; }
		if(cidade!=null && cidade.getPkCidade() >0)	 	{ querySQL += " AND fkcidade = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		if(cep!=null && cep >0) 
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, cep);
		}
		
		if(logradouro!=null && logradouro.length()>0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, logradouro);
		}
		
		if(bairro!=null && bairro.length()>0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, bairro);
		}
		
		if(numero!=null && numero.length() >0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, numero);
		}
		
		if(complemento!=null && complemento.length() >0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, complemento);
		}
		
		if(tipo!=null && tipo.length() >0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, tipo);
		}
		
		if(cidade!=null && cidade.getPkCidade() >0)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, cidade.getPkCidade());
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkendereco");
		}
		
		return null;
	}
	
	public Endereco retornEnderecoDados(Integer cep) {
		try {
			Endereco paramEndereco = new Endereco();

			String querySQL = "select "
					+ "	logradouro, "
					+ " numero, "
					+ " bairro, "
					+ " complemento "
					+ " from endereco "
					+ " where cep = ? "
					+ " limit 1";
			
			ps = con.prepareStatement(querySQL);
			ps.setInt(1, cep);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {				
				paramEndereco.setLogradouro(rs.getString("LOGRADOURO"));
				paramEndereco.setNumero(rs.getString("NUMERO"));
				paramEndereco.setBairro(rs.getString("BAIRRO"));
				paramEndereco.setComplemento(rs.getString("COMPLEMENTO"));
			}

			
			return paramEndereco;
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Erro ao consultar endereço, contate o administrador do sistema!", null));
			return null;
		}
	}
	
	/**
	 * Busca um endereço pelos parâmetros passados
	 * @author João Paulo
	 * @param pkEndereco
	 * @return Endereco
	 * @throws SQLException
	 */
	public Endereco buscarEndereco(Integer pkEndereco) throws SQLException
	{
		Cidade cidadeEndereco = null;
		Contato contato = null;
		Cidade cidadeEnderecoMunicipioCliente = null;
		Endereco endereco = null;
		String querySQL = "SELECT * FROM Endereco "
				+ " WHERE status = ?"
				+ " AND pkEndereco = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkEndereco);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			cidadeEndereco = new Cidade();
			contato = new Contato();
			cidadeEnderecoMunicipioCliente = new Cidade();
			endereco = new Endereco();
			endereco.setPkEndereco(rs.getInt("pkEndereco"));
			endereco.setCep(rs.getInt("cep"));
			endereco.setLogradouro(rs.getString("logradouro"));
			endereco.setBairro(rs.getString("bairro"));
			endereco.setNumero(rs.getString("numero"));
			endereco.setComplemento(rs.getString("complemento"));
			endereco.setTipo(rs.getString("tipo"));
			
			cidadeEndereco.setPkCidade(rs.getInt("fkCidade"));
			endereco.setCidade(cidadeEndereco);
			
			cidadeEnderecoMunicipioCliente.setPkCidade(rs.getInt("fkMunicipioCliente"));
			endereco.setFkMunicipioCliente(cidadeEnderecoMunicipioCliente);
			
			contato.setPkContato(rs.getInt("fkContato"));
			endereco.setContato(contato);
			
			return endereco;
		}
		
		return null;
	}
	
	/**
	 * Metodo para salvar o endereco da pessoa cadastrada
	 * @throws SQLException 
	 * 
	 * */
	public Endereco salvarEnderecoPessoa(Endereco dadosEndereco) throws SQLException {
		try {
			String querySQL;
			
			Integer numeroArgumentos = 1;
			
			querySQL = " INSERT INTO ENDERECO ( ";
			querySQL += " CEP, LOGRADOURO, BAIRRO, NUMERO, ";
			if(dadosEndereco.getComplemento() != null && !dadosEndereco.getComplemento().equals("")) {
				querySQL += " COMPLEMENTO, ";
			}
			querySQL += " TIPO, STATUS, FKCIDADE, FKMUNICIPIOCLIENTE, LATITUDE, LONGITUDE,  ";
			querySQL += " ENDERECOCOMPLETO ) values ( ";
			
			querySQL += " ?, ?, ?, ?, ";
			
			if(dadosEndereco.getComplemento() != null && !dadosEndereco.getComplemento().equals("")) {
				querySQL += " ?,";
			}
			
			querySQL += " ?, ?, ?, ?, ?, ?, ?) RETURNING PKENDERECO";
			
			ps = con.prepareStatement(querySQL.toString());
			
			ps.setInt(numeroArgumentos, dadosEndereco.getCep());
			numeroArgumentos++;

			ps.setString(numeroArgumentos, dadosEndereco.getLogradouro());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, dadosEndereco.getBairro());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, dadosEndereco.getNumero());
			numeroArgumentos++;
			
			if(dadosEndereco.getComplemento() != null && !dadosEndereco.getComplemento().equals("")) {				
				ps.setString(numeroArgumentos, dadosEndereco.getComplemento());
				numeroArgumentos++;
			}
			
			ps.setInt(numeroArgumentos, dadosEndereco.getRegiao().getPkRegiao());
			numeroArgumentos++;
			
			ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArgumentos++;
			
			ps.setInt(numeroArgumentos, dadosEndereco.getCidade().getPkCidade());
			numeroArgumentos++;
			
			ps.setInt(numeroArgumentos, dadosEndereco.getFkMunicipioCliente().getPkCidade());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, dadosEndereco.getLatitude());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, dadosEndereco.getLongitude());
			numeroArgumentos++;
			
			ps.setString(numeroArgumentos, dadosEndereco.getEnderecoCompleto());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dadosEndereco.setPkEndereco(rs.getInt("PKENDERECO"));
			}
			
			return dadosEndereco;
		} catch(SQLException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					e.toString(),null));
			con.rollback();
			return null;
		}
	}
}
