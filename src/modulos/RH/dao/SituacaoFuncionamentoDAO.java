package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.RH.om.SituacaoFuncionamento;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class SituacaoFuncionamentoDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de SITUACOES ECONOMICAS padrões */
	public List<SituacaoFuncionamento> consultaSituacaoFuncionamento() throws SQLException{
		
		List<SituacaoFuncionamento> listaSituFuncionamento = new ArrayList<SituacaoFuncionamento>();
		
		String querySQL = "SELECT * FROM SITUACAOFUNCIONAMENTO ORDER BY ORDEMEXIBICAO";
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery(querySQL);
		
		while (rs.next()){
			SituacaoFuncionamento paramSituFuncionamento = new SituacaoFuncionamento();
			paramSituFuncionamento.setPkSituacaoFuncionamento(rs.getInt("PKSITUACAOFUNCIONAMENTO"));
			paramSituFuncionamento.setCodigo(rs.getString("CODIGO"));
			paramSituFuncionamento.setDescricao(rs.getString("DESCRICAO"));
			paramSituFuncionamento.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaSituFuncionamento.add(paramSituFuncionamento);
		}
		
		return listaSituFuncionamento;
	}
}
