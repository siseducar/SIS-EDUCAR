package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Estado;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class EstadoDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * Este método eu busco os estados com país
	 * @author João Paulo
	 * @param pkEstado
	 * @param sigla
	 * @param nome
	 * @return Estado
	 * @throws SQLException
	 */
	public Estado obtemEstado(Integer pkEstado, String sigla, String nome) throws SQLException
	{
		Estado estado = null;
		Integer numeroArgumentos = 1;
		String querySQL = "SELECT * FROM estado"
				+ " WHERE status = ?";
		
		if(pkEstado!=null && pkEstado >0) 	 { querySQL += " AND pkEstado = ?"; }
		if(sigla!=null && sigla.length() >0) { querySQL += " AND sigla = ?"; }
		if(nome!=null && nome.length() >0)	 { querySQL += " AND nome = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(pkEstado!=null && pkEstado>0)
		{
			numeroArgumentos++;
			ps.setInt(numeroArgumentos, pkEstado);
		}
		
		if(sigla!=null && sigla.length()>0)
		{
			numeroArgumentos++;
			ps.setString(numeroArgumentos, sigla);
		}
		
		if(nome!=null && nome.length()>0)
		{
			numeroArgumentos++;
			ps.setString(numeroArgumentos, nome);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			estado = new Estado();
			
			estado.setPkEstado(rs.getInt("pkestado"));
			estado.setNome(rs.getString("nome"));
			estado.setSigla(rs.getString("sigla"));
			estado.setCodigoibge(rs.getInt("codigoibge"));
			estado.setStatus(rs.getInt("status"));
			estado.setPais(new PaisDAO().obtemPais(rs.getInt("fkPais"), null, null));
			
			fecharConexaoBanco(con, ps, true, false);
			return estado;
		}
		
		return null;
	}
	
	/*
	 * Metodo que busca todas os estados filtrando pelo pais
	 * 
	 * */
	public List<Estado> consultaEstado(Integer pkPais) throws SQLException{		
		
		List<Estado> listaEstado = new ArrayList<Estado>();
		String querySQL = "SELECT * FROM ESTADO WHERE FKPAIS = ? ORDER BY NOME";
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, pkPais);
		
		rs = ps.executeQuery();
		
		while (rs.next()){
			Estado paramEstado = new Estado();
			paramEstado.setPkEstado(rs.getInt("PKESTADO"));
			paramEstado.setNome(rs.getString("NOME"));
			
			listaEstado.add(paramEstado);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaEstado;
	}
	
	/*
	 * Metodo que busca todas os estados
	 * 
	 * */
	public List<Estado> listaEstado() throws SQLException{		
		
		List<Estado> listaEstado = new ArrayList<Estado>();
		String querySQL = "SELECT * FROM ESTADO ORDER BY NOME";
		
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while (rs.next()){
			Estado paramEstado = new Estado();
			paramEstado.setPkEstado(rs.getInt("PKESTADO"));
			paramEstado.setNome(rs.getString("NOME"));
			
			listaEstado.add(paramEstado);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaEstado;
	}
}
