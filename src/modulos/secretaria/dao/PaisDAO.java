package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Pais;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class PaisDAO extends SisEducarDAO
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/**
	 * Busca o país de acordo com os parâmetros passado
	 * @author João Paulo
	 * @param sigla
	 * @param nome
	 * @return Pais
	 * @throws SQLException
	 */
	public Pais obtemPais(Integer pkPais, String sigla, String nome) throws SQLException
	{
		Pais pais = null;
		Integer numeroArgumentos = 1;
		String querySQL = "SELECT * FROM pais"
				+ " WHERE status = ?";
		
		if(pkPais!=null && pkPais >0)		 { querySQL += " AND pkpais = ?"; }
		if(sigla!=null && sigla.length() >0) { querySQL += " AND sigla = ?"; }
		if(nome!=null && nome.length() >0)	 { querySQL += " AND nome = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(pkPais!=null && pkPais >0)
		{
			numeroArgumentos++;
			ps.setInt(numeroArgumentos, pkPais);
		}
		
		if(sigla!=null && sigla.length()>0)
		{
			numeroArgumentos++;
			ps.setString(numeroArgumentos, sigla);
		}
		
		if(nome!=null && nome.length()>0)
		{
			numeroArgumentos++;
			ps.setString(4, nome);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			pais = new Pais();
			
			pais.setPkPais(rs.getInt("pkpais"));
			pais.setNome(rs.getString("nome"));
			pais.setSigla(rs.getString("sigla"));
			pais.setCodigobacem(rs.getInt("codigobacem"));
			pais.setStatus(rs.getInt("status"));
			return pais;
		}
		
		return null;
	}
	
	
	
	public List<Pais> consultaPais() throws SQLException{		
		List<Pais> listaPais = new ArrayList<Pais>();
		String querySQL = "SELECT * FROM PAIS WHERE STATUS = ? ORDER BY NOME";

		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		rs = ps.executeQuery();
		
		while (rs.next()){
			Pais paramPais = new Pais();
			paramPais.setPkPais(rs.getInt("PKPAIS"));
			paramPais.setNome(rs.getString("NOME"));
			paramPais.setSigla(rs.getString("SIGLA"));
			
			listaPais.add(paramPais);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaPais;
	}
}
