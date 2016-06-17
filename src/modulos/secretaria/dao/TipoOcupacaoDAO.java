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
import modulos.sisEducar.utils.ConstantesSisEducar;

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
	
	/**
	 * Busca um tipo ocupação pelos parâmetros passados
	 * @author João Paulo
	 * @param pkTipoOcupacao
	 * @return TipoOcupacao
	 * @throws SQLException
	 */
	public TipoOcupacao buscarTipoOcupacao(Integer pkTipoOcupacao) throws SQLException
	{
		TipoOcupacao tipoOcupacao = null;
		String querySQL = "SELECT * FROM TipoOcupacao "
				+ " WHERE status = ?"
				+ " AND pkTipoOcupacao = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkTipoOcupacao);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			tipoOcupacao = new TipoOcupacao();
			tipoOcupacao.setPkTipoOcupacao(rs.getInt("pkTipoOcupacao"));
			tipoOcupacao.setCodigo(rs.getString("codigo"));
			tipoOcupacao.setDescricao(rs.getString("descricao"));
			tipoOcupacao.setStatus(rs.getInt("status"));
			tipoOcupacao.setOrdemExibicao(rs.getInt("ordemExibicao"));
			
			return tipoOcupacao;
		}
		
		return null;
	}
}
