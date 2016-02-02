package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modulos.RH.om.UnidadeEscolar;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class UnidadeEscolarDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public UnidadeEscolarDAO() throws SQLException
	{
		desabilitarAutoCommit(con);
	}
	
	/**
	 * Insere a unidade e já obtem a pk salva e retorna o objeto completo
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @return unidadeEscolar
	 */
	public UnidadeEscolar inserirUnidadeEscolar(UnidadeEscolar unidadeEscolar)
	{
		try 
		{
			Integer pkUnidadeEscolarExistente = obtemPKUnidadeEscolar(unidadeEscolar.getCodigo(), unidadeEscolar.getNome());
			if(pkUnidadeEscolarExistente==null)
			{
				String querySQL = "INSERT INTO unidadeescolar "
						+ " (codigo, nome, status) values(CAST( ? as int),?,?)";
				
				ps = con.prepareStatement(querySQL);
				
				ps.setString(1, unidadeEscolar.getCodigo());
				ps.setString(2, unidadeEscolar.getNome());
				ps.setInt(3, ConstantesSisEducar.STATUS_ATIVO);
				
				fecharConexaoBanco(con, ps, false, true);
			}

			unidadeEscolar.setPkUnidadeEscolar(obtemPKUnidadeEscolar(unidadeEscolar.getCodigo(), unidadeEscolar.getNome()));
			
			return unidadeEscolar;
		} 
		catch (SQLException e) 
		{
			return null;
		}
	}
	
	/**
	 * Busca apenas a pk do endereço a partir dos parâmetros recebidos
	 * @author João Paulo
	 * @param cep
	 * @param logradouro
	 * @param bairro
	 * @param numero
	 * @param complemento
	 * @param tipo
	 * @param fkCidade
	 * @return Integer pkEndereco
	 * @throws SQLException
	 */
	public Integer obtemPKUnidadeEscolar(String codigo, String nome) throws SQLException
	{
		Integer numeroArgumentos = 1;
		String querySQL = "SELECT * FROM unidadeescolar"
				+ " WHERE status = ?";
		
		if(codigo!=null && codigo.length() >0) 						{ querySQL += " AND codigo = CAST(? as int)"; }
		if(nome!=null && nome.length() >0)							{ querySQL += " AND nome = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(codigo!=null && codigo.length() >0)
		{
			numeroArgumentos ++;
			ps.setString(numeroArgumentos, codigo);
		}
		
		if(nome!=null && nome.length() >0)
		{
			numeroArgumentos ++;
			ps.setString(numeroArgumentos, nome);
		}
		
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			return rs.getInt("pkunidadeescolar");
		}
		
		return null;
	}
}
