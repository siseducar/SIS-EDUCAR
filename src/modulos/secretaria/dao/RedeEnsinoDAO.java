package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.RedeEnsino;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class RedeEnsinoDAO extends SisEducarDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de REDES DE ENSINO padrões */
	public List<RedeEnsino> consultaRedeEnsino() throws SQLException{
		
		List<RedeEnsino> listaRedeEnsino = new ArrayList<RedeEnsino>();
		
		String querySQL = "SELECT * FROM REDEENSINO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		rs = ps.executeQuery();
		
		while (rs.next()){
			RedeEnsino paramRedeEnsino = new RedeEnsino();
			paramRedeEnsino.setPkRedeEnsino(rs.getInt("PKREDEENSINO"));
			paramRedeEnsino.setNome(rs.getString("NOME"));
			
			listaRedeEnsino.add(paramRedeEnsino);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaRedeEnsino;
	}
	
	/**
	 * Busca uma rede de ensino pelos filtros passados como parâmetro
	 * @author João Paulo
	 * @param pkRedeEnsino
	 * @return RedeEnsino
	 * @throws SQLException
	 */
	public RedeEnsino buscarRedeEnsino(Integer pkRedeEnsino) throws SQLException
	{
		RedeEnsino redeEnsino = null;
		Cidade cidade = null;
		String querySQL = "SELECT * FROM RedeEnsino "
				+ " WHERE status = ?"
				+ " AND pkRedeEnsino = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkRedeEnsino);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			cidade = new Cidade();
			redeEnsino = new RedeEnsino();
			redeEnsino.setPkRedeEnsino(rs.getInt("pkRedeEnsino"));
			redeEnsino.setDescricao(rs.getString("descricao"));
			redeEnsino.setNome(rs.getString("nome"));
			redeEnsino.setStatus(rs.getInt("status"));
			redeEnsino.setOrdemExibicao(rs.getInt("ordemExibicao"));
			cidade.setPkCidade(rs.getInt("fkMunicipioCliente"));
			redeEnsino.setFkMunicipioCliente(cidade);
			
			return redeEnsino;
		}
		
		return null;
	}
}
