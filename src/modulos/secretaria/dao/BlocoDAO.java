package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Bloco;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class BlocoDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os blocos do ambiente */
	public List<Bloco> consultaBloco() throws SQLException 
	{
		List<Bloco> listaTipos = new ArrayList<>();
		
		String querySQL = "SELECT * FROM Bloco WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";

		ps = con.prepareStatement(querySQL.toString());
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Bloco paramTipo = new Bloco();
			paramTipo.setPkBloco(rs.getInt("PKBLOCO"));
			paramTipo.setCodigo(rs.getString("CODIGO"));
			paramTipo.setDescricao(rs.getString("DESCRICAO"));
						
			listaTipos.add(paramTipo);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaTipos;
	}
}
