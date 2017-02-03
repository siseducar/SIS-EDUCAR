package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import modulos.secretaria.om.Contato;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class ContatoDAO extends SisEducarDAO
{

	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/**
	 * Busca um contato pela pk dele
	 * @author João Paulo
	 * @param pkContato
	 * @return
	 * @throws SQLException
	 */
	public Contato buscarContato(Contato contatoDados)
	{
		try {
			Contato contato = null;
			String querySQL = "SELECT * FROM CONTATO "
					+ " WHERE PKCONTATO = ?";
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, contatoDados.getPkContato());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				contato = new Contato();
				contato.setPkContato(rs.getInt("PKCONTATO"));
				contato.setTelCelular(rs.getString("TELCELULAR"));
				contato.setTelComercial(rs.getString("TELCOMERCIAL"));
				contato.setTelResidencial(rs.getString("TELRESIDENCIAL"));
				contato.setEmail(rs.getString("EMAIL"));
				
				fecharConexaoBanco(con, ps, false, true);
				fecharConexaoBanco(con, ps, true, false);
				
				return contato;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Insere um contato no banco de dados
	 * @author João Paulo
	 * @param contato
	 * @return
	 */
	public Contato inserirContato(Contato contato)
	{
		try 
		{
			String querySQL = "INSERT INTO contato "
					+ " (telComercial, telResidencial, telCelular, email, enviarNotificacao, status) values(?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setObject(1, contato.getTelComercial()!=null ? contato.getTelComercial() : "");
			ps.setObject(2, contato.getTelResidencial() !=null ? contato.getTelResidencial() : "");
			ps.setObject(3, contato.getTelCelular() !=null ? contato.getTelCelular() : "");
			ps.setObject(4, contato.getEmail() !=null ? contato.getEmail() : "");
			ps.setObject(5, contato.getEnviarNotificacao() !=null ? contato.getEnviarNotificacao() : true);
			ps.setInt(6, ConstantesSisEducar.STATUS_ATIVO);
			
			fecharConexaoBanco(con, ps, false, true);

			contato.setPkContato(obtemPKContato(contato.getTelCelular(), contato.getTelComercial(), contato.getTelResidencial(), contato.getEmail()));
			
			return contato;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	/**
	 * Atualiza o contato a partir da pk do contato
	 * @author João Paulo
	 * @param contato
	 * @return Contato
	 */
	public Contato atualizarContato(Contato contato)
	{
		try 
		{
			String querySQL = "UPDATE contato "
					+ " SET (telComercial, telResidencial, telCelular, email, enviarNotificacao) = "
					+ " (?,?,?,?,?) "
					+ " WHERE pkContato = ?";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setObject(1, contato.getTelComercial()!=null ? contato.getTelComercial() : "");
			ps.setObject(2, contato.getTelResidencial() !=null ? contato.getTelResidencial() : "");
			ps.setObject(3, contato.getTelCelular() !=null ? contato.getTelCelular() : "");
			ps.setObject(4, contato.getEmail() !=null ? contato.getEmail() : "");
			ps.setObject(5, contato.getEnviarNotificacao() !=null ? contato.getEnviarNotificacao() : true);
			ps.setInt(6, contato.getPkContato());
			
			fecharConexaoBanco(con, ps, false, true);
			
			return contato;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	/**
	 * Localiza o contato e trás a PK dele
	 * @author João Paulo
	 * @param telCelular
	 * @param telComercial
	 * @param telResidencial
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public Integer obtemPKContato(String telCelular, String telComercial, String telResidencial, String email) throws SQLException
	{
		Integer numeroArgumentos = 1;
		
		String querySQL = "SELECT * FROM contato"
				+ " WHERE status = ?";
		
		if(telCelular!=null && telCelular.length() >0) 						{ querySQL += " AND telCelular = ?"; }
		if(telComercial!=null && telComercial.length() >0)					{ querySQL += " AND telComercial = ?"; }
		if(telResidencial!=null && telResidencial.length() >0)		 		{ querySQL += " AND telResidencial = ?"; }
		if(email!=null && email.length() >0)								{ querySQL += " AND email = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		if(telCelular!=null && telCelular.length()>0) 
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, telCelular);
		}
		
		if(telComercial!=null && telComercial.length()>0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, telComercial);
		}
		
		if(telResidencial!=null && telResidencial.length()>0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, telResidencial);
		}
		
		if(email!=null && email.length() >0)	
		{ 
			numeroArgumentos ++; 
			ps.setString(numeroArgumentos, email);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkcontato");
		}
		
		return null;
	}
	
	/*
	 * Metodo para salvar os dados de contato no cadastro da pessoa
	 * */
	public Contato salvarContato(Contato dadosContato) {
		try {
			int numeroArgumentos = 1;
			String querySQL = "INSERT INTO CONTATO ( ";
				if( dadosContato.getTelResidencial() != null && !dadosContato.getTelResidencial().equals("") ) {
					querySQL+= " TELRESIDENCIAL, ";
				}
				if( dadosContato.getTelCelular() != null && !dadosContato.getTelCelular().equals("") ) {
					querySQL+= " TELCELULAR, ";
				}
				if( dadosContato.getTelComercial() != null && !dadosContato.getTelComercial().equals("") ) {
					querySQL+= " TELCOMERCIAL, ";
				}
				if( dadosContato.getEmail() != null && !dadosContato.getEmail().equals("") ) {
					querySQL+= " EMAIL, ";
				}
				if( dadosContato.getEmailempresa() != null && !dadosContato.getEmailempresa().equals("") ) {
					querySQL+= " EMAILEMPRESA, ";
				}
				if( dadosContato.getSiteempresa() != null && !dadosContato.getSiteempresa().equals("") ) {
					querySQL+= " SITEEMPRESA, ";
				}
				if( dadosContato.getFax() != null && !dadosContato.getFax().equals("") ) {
					querySQL+= " FAX, ";
				}
				
				querySQL+= " STATUS ";
				
				querySQL+= " ) values( ";
				if( dadosContato.getTelResidencial() != null && !dadosContato.getTelResidencial().equals("") ) {
					querySQL+= " ?, ";
				}
				if( dadosContato.getTelCelular() != null && !dadosContato.getTelCelular().equals("") ) {
					querySQL+= " ?, ";
				}
				if( dadosContato.getTelComercial() != null && !dadosContato.getTelComercial().equals("") ) {
					querySQL+= " ?, ";
				}
				if( dadosContato.getEmail() != null && !dadosContato.getEmail().equals("") ) {
					querySQL+= " ?, ";
				}
				if( dadosContato.getEmailempresa() != null && !dadosContato.getEmailempresa().equals("") ) {
					querySQL+= " ?, ";
				}
				if( dadosContato.getSiteempresa() != null && !dadosContato.getSiteempresa().equals("") ) {
					querySQL+= " ?, ";
				}
				if( dadosContato.getFax() != null && !dadosContato.getFax().equals("") ) {
					querySQL+= " ?, ";
				}
				querySQL += "? ) RETURNING PKCONTATO ";
			
			ps = con.prepareStatement(querySQL);
			
			if( dadosContato.getTelResidencial() != null && !dadosContato.getTelResidencial().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getTelResidencial());
				numeroArgumentos++;
			}
			if( dadosContato.getTelCelular() != null && !dadosContato.getTelCelular().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getTelCelular());
				numeroArgumentos++;
			}
			if( dadosContato.getTelComercial() != null && !dadosContato.getTelComercial().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getTelComercial());
				numeroArgumentos++;
			}
			if( dadosContato.getEmail() != null && !dadosContato.getEmail().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getEmail());
				numeroArgumentos++;
			}
			if( dadosContato.getEmailempresa() != null && !dadosContato.getEmailempresa().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getEmailempresa());
				numeroArgumentos++;
			}
			if( dadosContato.getSiteempresa() != null && !dadosContato.getSiteempresa().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getSiteempresa());
				numeroArgumentos++;
			}
			if( dadosContato.getFax() != null && !dadosContato.getFax().equals("") ) {
				ps.setString(numeroArgumentos, dadosContato.getFax());
				numeroArgumentos++;
			}			
			ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				dadosContato.setPkContato(rs.getInt("PKCONTATO"));
			}
			
			fecharConexaoBanco(con, ps, true, false);
			
			return dadosContato;
		} catch (Exception e) {
			return null;
		}
	}
		
	/*
	 * Metodo para atualizar os dados de contato
	 * */
	public Boolean atualizarContatoPessoa(Contato dadosContato) throws SQLException {
		try {
			int numeroArgumentos = 1;
			
			String querySQL = "UPDATE CONTATO SET";
				if(dadosContato.getTelResidencial() != null && !dadosContato.getTelResidencial().equals("")) {
					querySQL += " TELRESIDENCIAL = ?, ";
				}
				if(dadosContato.getTelCelular() != null && !dadosContato.getTelCelular().equals("")) {
					querySQL += " TELCELULAR = ?, ";
				}
				if(dadosContato.getEmail() != null && !dadosContato.getEmail().equals("")) {
					querySQL += " EMAIL = ? ";
				}
				querySQL += " WHERE STATUS = ? ";
				querySQL += " AND PKCONTATO = ?";
			
			ps = con.prepareStatement(querySQL);
			
			if(dadosContato.getTelResidencial() != null && !dadosContato.getTelResidencial().equals("")) {
				ps.setString(numeroArgumentos, dadosContato.getTelResidencial());
				numeroArgumentos++;
			}
			if(dadosContato.getTelCelular() != null && !dadosContato.getTelCelular().equals("")) {
				ps.setString(numeroArgumentos, dadosContato.getTelCelular());
				numeroArgumentos++;
			}
			if(dadosContato.getEmail() != null && !dadosContato.getEmail().equals("")) {
				ps.setString(numeroArgumentos, dadosContato.getEmail());
				numeroArgumentos++;
			}
			
			ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
			numeroArgumentos++;
			
			ps.setInt(numeroArgumentos, dadosContato.getPkContato());
			
			ps.execute();
			
			return true;
		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					e.toString(),null));
			return false;
		} finally {
			con.close();
		}
	}
	
	/*
	 * Metodo para deletar os dados de contato
	 * */
	public void deletarContato(Integer pkContato) {
		try {
			String querySQL = "DELETE FROM CONTATO "
					+ "WHERE STATUS = ? "
					+ "AND PKCONTATO = ?";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
			ps.setInt(2, pkContato);
			
			ps.executeQuery();
			fecharConexaoBanco(con, ps, false, true);
			fecharConexaoBanco(con, ps, true, false);
		}
		catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					e.toString(),null));
		}
	}
}
