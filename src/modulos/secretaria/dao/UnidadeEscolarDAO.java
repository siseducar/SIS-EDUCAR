package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.UnidadeEscolar;
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
				Integer numeroArgumentos = 1;
				String querySQL = "INSERT INTO unidadeescolar "
						+ " (codigo, nome, status, fkredeensino, fkregiao, fksituacaofuncionamento, unidadecontrolada, unidadeinformatizada, fktipoocupacao, fkterreno, fkDiretor, fkMunicipioCliente) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
				
				ps = con.prepareStatement(querySQL);
				
				ps.setString(numeroArgumentos, unidadeEscolar.getCodigo());
				numeroArgumentos++;
				
				ps.setString(numeroArgumentos, unidadeEscolar.getNome());
				numeroArgumentos++;
				
				ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
				numeroArgumentos++;
				
				if(unidadeEscolar.getRedeEnsino()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getRedeEnsino().getPkRedeEnsino());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				if(unidadeEscolar.getRegiao()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getRegiao().getPkRegiao());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				if(unidadeEscolar.getSituacaoFuncionamento()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getSituacaoFuncionamento().getPkSituacaoFuncionamento());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				ps.setBoolean(numeroArgumentos, unidadeEscolar.getUnidadeControlada());
				numeroArgumentos++;
				
				ps.setBoolean(numeroArgumentos, unidadeEscolar.getUnidadeInformatizada());
				numeroArgumentos++;
				
				if(unidadeEscolar.getTipoOcupacao()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getTipoOcupacao().getPkTipoOcupacao());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
				if(unidadeEscolar.getTerreno()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getTerreno().getPkTerreno());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				if(unidadeEscolar.getDiretor()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getDiretor().getPkPessoa());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				if(unidadeEscolar.getFkMunicipioCliente()!=null)
				{
					ps.setInt(numeroArgumentos, unidadeEscolar.getFkMunicipioCliente().getPkCidade());
				}
				else
				{
					ps.setObject(numeroArgumentos, null);
				}
				numeroArgumentos++;
				
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
		
		if(codigo!=null && codigo.length() >0) 	{  querySQL += " AND codigo = ?"; }
		if(nome!=null && nome.length() >0)		{ querySQL += " AND nome = ?"; }
		
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
	
	/* Metodo para retornar os tipos de UNIDADES ESCOLARES de acordo com a rede */
	public List<UnidadeEscolar> consultaUnidadeEscolar(Integer pkRedeEnsino) throws SQLException{
		
		List<UnidadeEscolar> listaUnidadeEscolar = new ArrayList<UnidadeEscolar>();
		
		String querySQL = "SELECT * FROM UNIDADEESCOLAR WHERE FKREDEENSINO = ? ORDER BY NOME";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, pkRedeEnsino);
		rs = ps.executeQuery();
		
		while (rs.next()){
			UnidadeEscolar paramUnidadeEscolar = new UnidadeEscolar();
			paramUnidadeEscolar.setPkUnidadeEscolar(rs.getInt("PKUNIDADEESCOLAR"));
			paramUnidadeEscolar.setNome(rs.getString("NOME"));
			
			listaUnidadeEscolar.add(paramUnidadeEscolar);
		}
		
		return listaUnidadeEscolar;
	}
}
