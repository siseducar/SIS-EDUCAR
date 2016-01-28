package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.Endereco;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class EnderecoDAO extends SisEducarDAO 
{
	// Realizando conex�o com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public EnderecoDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	public Endereco inserirEndereco(Endereco endereco)
	{
		try 
		{
			String querySQL = "INSERT INTO endereco "
					+ " (cep, logradouro, bairro, numero, complemento, tipo, status, fkcidade) values(?,?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, endereco.getCep());
			ps.setString(2, endereco.getLogradouro());
			ps.setString(3, endereco.getBairro());
			ps.setInt(4, endereco.getNumero());
			ps.setString(5, endereco.getComplemento());
			ps.setInt(6, 0);
			ps.setInt(7, ConstantesSisEducar.STATUS_ATIVO);
			ps.setInt(8, endereco.getCidade().getPkCidade());
			
			fecharConexaoBanco(con, ps, false, true);
			
			endereco.setPkEndereco(obtemPKEndereco(endereco.getCep(), endereco.getLogradouro(), endereco.getBairro(), endereco.getNumero(), endereco.getComplemento(), endereco.getTipo(), 
					endereco.getCidade().getPkCidade()));
			
			return endereco;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	public Integer obtemPKEndereco(Integer cep, String logradouro, String bairro, Integer numero, String complemento, String tipo, Integer fkCidade) throws SQLException
	{
		Integer numeroArgumentos = 1;
		
		String querySQL = "SELECT * FROM endereco"
				+ " WHERE status = ?";
		
		if(cep!=null && cep >0) 						{ querySQL += " AND cep = ?"; }
		if(logradouro!=null && logradouro.length() >0)	{ querySQL += " AND logradouro = ?"; }
		if(bairro!=null && bairro.length() >0)		 	{ querySQL += " AND bairro = ?"; }
		if(numero!=null && numero >0)					{ querySQL += " AND numero = ?"; }
		if(complemento!=null && complemento.length() >0){ querySQL += " AND complemento = ?"; }
		if(tipo!=null && tipo.length() >0)	 					{ querySQL += " AND tipo = ?"; }
		if(fkCidade!=null && fkCidade >0)	 			{ querySQL += " AND fkcidade = ?"; }
		
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
		
		if(numero!=null && numero >0)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, numero);
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
		
		if(fkCidade!=null && fkCidade >0)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, fkCidade);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkendereco");
		}
		
		return null;
	}
}
