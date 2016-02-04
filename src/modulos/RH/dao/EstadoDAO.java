package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.Estado;
import modulos.RH.om.Pais;
import modulos.RH.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class EstadoDAO extends SisEducarDAO 
{
	// Realizando conex�o com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	/**
	 * Este m�todo eu busco os estados com pa�s
	 * @author Jo�o Paulo
	 * @param pkEstado
	 * @param sigla
	 * @param nome
	 * @return Estado
	 * @throws SQLException
	 */
	public Estado obtemEstado(Integer pkEstado, String sigla, String nome) throws SQLException
	{
		Estado estado = null;
		Pais pais = null;
		String querySQL = "SELECT * FROM estado"
				+ " WHERE status = ?";
		
		if(pkEstado!=null && pkEstado >0) 	 { querySQL += " AND pkEstado = ?"; }
		if(sigla!=null && sigla.length() >0) { querySQL += " AND sigla = ?"; }
		if(nome!=null && nome.length() >0)	 { querySQL += " AND nome = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkEstado);
		ps.setString(3, sigla);
		ps.setString(4, nome);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			estado = new Estado();
			
			estado.setPkEstado(rs.getInt("pkestado"));
			estado.setNome(rs.getString("nome"));
			estado.setSigla(rs.getString("sigla"));
			estado.setCodigoibge(rs.getInt("codigoibge"));
			estado.setStatus(rs.getInt("status"));
			estado.setPais(new PaisDAO().obtemPais(rs.getInt("fkpais"), null, null));
			return estado;
		}
		
		return null;
	}
	
	public List<Estado> consultaEstado() throws SQLException{		
		List<Estado> listaEstado = new ArrayList<Estado>();
		String querySQL = "SELECT * FROM ESTADO ORDER BY NOME";
		Statement stm;
		stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while (rs.next()){
			Estado paramEstado = new Estado();
			paramEstado.setPkEstado(rs.getInt("PKESTADO"));
			paramEstado.setNome(rs.getString("NOME"));
			paramEstado.setSigla(rs.getString("SIGLA"));
			
			listaEstado.add(paramEstado);
		}
		
		return listaEstado;
	}
}
