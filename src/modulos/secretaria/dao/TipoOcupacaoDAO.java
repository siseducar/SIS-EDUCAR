package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.TipoOcupacao;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class TipoOcupacaoDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de TIPOS OCUPACAO padrões */
	public List<TipoOcupacao> consultaTipoOcupacao() throws SQLException{
		
		List<TipoOcupacao> listaTipoOcupacao = new ArrayList<TipoOcupacao>();
		
		String querySQL = "SELECT * FROM TIPOOCUPACAO ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while (rs.next()){
			TipoOcupacao paramTipoOcupacao = new TipoOcupacao();
			paramTipoOcupacao.setPkTipoOcupacao(rs.getInt("PKTIPOOCUPACAO"));
			paramTipoOcupacao.setCodigo(rs.getString("CODIGO"));
			paramTipoOcupacao.setDescricao(rs.getString("DESCRICAO"));
			paramTipoOcupacao.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaTipoOcupacao.add(paramTipoOcupacao);
		}
		
		return listaTipoOcupacao;
	}
}
