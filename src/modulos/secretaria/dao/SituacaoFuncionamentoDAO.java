package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.SituacaoFuncionamento;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class SituacaoFuncionamentoDAO extends SisEducarDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de SITUACOES ECONOMICAS padrões */
	public List<SituacaoFuncionamento> consultaSituacaoFuncionamento() throws SQLException{
		
		List<SituacaoFuncionamento> listaSituFuncionamento = new ArrayList<SituacaoFuncionamento>();
		
		String querySQL = "SELECT * FROM SITUACAOFUNCIONAMENTO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		rs = ps.executeQuery();
		
		while (rs.next()){
			SituacaoFuncionamento paramSituFuncionamento = new SituacaoFuncionamento();
			paramSituFuncionamento.setPkSituacaoFuncionamento(rs.getInt("PKSITUACAOFUNCIONAMENTO"));
			paramSituFuncionamento.setCodigo(rs.getString("CODIGO"));
			paramSituFuncionamento.setDescricao(rs.getString("DESCRICAO"));
			paramSituFuncionamento.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaSituFuncionamento.add(paramSituFuncionamento);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaSituFuncionamento;
	}
	
	/**
	 * Busca uma situação de funcionamento pelos parâmetros passados
	 * @author João Paulo
	 * @param pkSituacaoFuncionamento
	 * @return SituacaoFuncionamento
	 * @throws SQLException
	 */
	public SituacaoFuncionamento buscarSituacaoFuncionamento(Integer pkSituacaoFuncionamento) throws SQLException
	{
		SituacaoFuncionamento situacaoFuncionamento = null;
		String querySQL = "SELECT * FROM SituacaoFuncionamento "
				+ " WHERE status = ?"
				+ " AND pkSituacaoFuncionamento = ?";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, pkSituacaoFuncionamento);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			situacaoFuncionamento = new SituacaoFuncionamento();
			situacaoFuncionamento.setPkSituacaoFuncionamento(rs.getInt("pkSituacaoFuncionamento"));
			situacaoFuncionamento.setCodigo(rs.getString("codigo"));
			situacaoFuncionamento.setDescricao(rs.getString("descricao"));
			situacaoFuncionamento.setStatus(rs.getInt("status"));
			situacaoFuncionamento.setOrdemExibicao(rs.getInt("ordemExibicao"));
			
			fecharConexaoBanco(con, ps, false, false);
			
			return situacaoFuncionamento;
		}
		
		return null;
	}
}
