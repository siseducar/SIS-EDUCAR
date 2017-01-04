package modulos.sisEducar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.om.ChaveAcesso;
import modulos.sisEducar.om.Versao;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
		
	/**
	 * Este metodo recebe como parametro uma conexao com o banco de dados para encerrar a mesma
	 * @param con
	 * @throws SQLException
	 */
	public void desabilitarAutoCommit(Connection con) throws SQLException
	{
		con.setAutoCommit(false);
	}
	
	/**
	 * O método é usado para fechar a conexão com o banco recebendo como parametro a conexão, o preparedStatment e assim a resposta se fechara a conexão e também
	 * se executara um commit
	 * @param Connection con
	 * @param PreparedStatement ps
	 * @param TRUE || FALSE fecharConexao
	 * @param TRUE || FALSE commit
	 * @throws SQLException
	 */
	public void fecharConexaoBanco(Connection con, PreparedStatement ps, Boolean fecharConexao, Boolean commit) throws SQLException
	{
		if(con !=null && ps !=null)
		{
			if(fecharConexao)
			{
				ps.close();
				con.close();
			}
			if(commit)
			{
				ps.execute();
				con.commit();
			}
		}
	}
	
	/**
	 * Busca a chave de acesso do usuário
	 * @return
	 * @throws SQLException
	 */
	public ChaveAcesso obtemChaveAcesso(Usuario usuario) throws SQLException
	{
		ChaveAcesso chaveAcesso = null;
		Cidade municipioCliente = null;
		String querySQL = "SELECT * FROM ChaveAcesso "
						+ "WHERE status = ?"
						+ "AND fkMunicipioCliente = ?";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		ps.setInt(2, usuario.getFkMunicipioCliente().getPkCidade());
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
		{
			municipioCliente = new Cidade();
			municipioCliente.setPkCidade(rs.getInt("fkMunicipioCliente"));
			
			chaveAcesso = new ChaveAcesso();
			chaveAcesso.setPkChaveAcesso(rs.getInt("pkChaveAcesso"));
			chaveAcesso.setChave(rs.getString("chave"));
			chaveAcesso.setMunicipioCliente(municipioCliente);
			
			return chaveAcesso;
		}
		
		return null;
	}
	
	/**
	 * Busca as últimas 5 versões do sistema
	 * @author João Paulo
	 * @return
	 * @throws SQLException
	 */
	public List<Versao> pesquisarVersoes() throws SQLException
	{
		Versao versao = null;
		List<Versao> versoes = new ArrayList<Versao>();
		String querySQL = "SELECT * FROM Versao "
				+ "WHERE status = ?"
				+ "ORDER BY data DESC LIMIT 5";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) 
		{
			versao = new Versao();
			versao.setVersao(rs.getString("versao"));
			versao.setDescricao(rs.getString("descricao"));
			versao.setData(rs.getDate("data"));
			versao.setVisualizado(rs.getBoolean("visualizado"));
			
			versoes.add(versao);
		}
		
		return versoes;
	}
	
	public void atualizarVersoesVisualizados() throws SQLException
	{
		String querySQL = "UPDATE Versao "
				+ " SET VISUALIZADO = TRUE"
				+ " WHERE STATUS = ? RETURNING STATUS";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);
		
		ps.executeQuery();
	}
	
	/**
	 * Obtem o próximo código disponível de uma determinada tabela
	 * @author João Paulo
	 * @param nomeTabela
	 * @param status
	 * @return CODIGO(String)
	 * @throws SQLException
	 */
	public String obtemCodigoDisponivel(String nomeTabela, Integer status) throws SQLException
	{
		//CASO NÃO TIVER NENHUM REGISTRO ENTÃO O PADRÃO DO PRIMEIRO NÚMERO SERÁ 1
		String numeroRetorno = "1";
		Integer numeroAux = 0;
		String querySQL = "SELECT * FROM "
				+ nomeTabela
				+ " WHERE status = ?"
				+ " ORDER BY CODIGO DESC"
				+ " LIMIT 1";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, status);
		
		ResultSet rs = ps.executeQuery();
		if(rs.next()) 
		{
			numeroRetorno = rs.getString("CODIGO");
			numeroAux = new Integer(numeroRetorno);
			numeroAux++;
			numeroRetorno = numeroAux.toString();
		}
		
		return numeroRetorno;
	}
}
