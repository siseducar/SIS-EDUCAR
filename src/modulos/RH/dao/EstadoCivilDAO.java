package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.EstadoCivil;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class EstadoCivilDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de ESTADO CIVIL padrao */
	public List<EstadoCivil> consultaEstaCivil() throws SQLException{
		
		List<EstadoCivil> listaEstaCivil = new ArrayList<>();
		
		String querySQL = "SELECT * FROM ESTADOCIVIL ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while(rs.next()){
			EstadoCivil paramEstCivil = new EstadoCivil();
			paramEstCivil.setPkEstadoCivil(rs.getInt("PKESTADOCIVIL"));
			paramEstCivil.setCodigo(rs.getString("CODIGO"));
			paramEstCivil.setDescricao(rs.getString("DESCRICAO"));
			paramEstCivil.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaEstaCivil.add(paramEstCivil);
		}
		
		return listaEstaCivil;
	}
}
