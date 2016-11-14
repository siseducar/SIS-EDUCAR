package modulos.secretaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.secretaria.om.Turno;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class TurnoDAO extends SisEducarDAO {

	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	/* Metodo para retornar os tipos de TURNOS padrões */
	public List<Turno> consultaTurno() throws SQLException{
		
		List<Turno> listaTurno = new ArrayList<Turno>();
		
		String querySQL = "SELECT * FROM TURNO WHERE STATUS = ? ORDER BY ORDEMEXIBICAO";
		
		ps = con.prepareStatement(querySQL);
		ps.setInt(1, ConstantesSisEducar.STATUS_ATIVO);			
		rs = ps.executeQuery();
		
		while (rs.next()){
			Turno paramTurno = new Turno();
			
			paramTurno.setPkTurno(rs.getInt("PKTURNO"));
			paramTurno.setCodigo(rs.getString("CODIGO"));
			paramTurno.setDescricao(rs.getString("DESCRICAO"));
			paramTurno.setOrdemExibicao(rs.getInt("ORDEMEXIBICAO"));
			
			listaTurno.add(paramTurno);
		}
		
		fecharConexaoBanco(con, ps, true, false);
		
		return listaTurno;
	}
	
	/**
	 * Obtem um turno com apenas algumas informações
	 * @author João Paulo
	 * @param pkTurno
	 * @param nome
	 * @return
	 */
	public Turno obtemSimples(Integer pkTurno, String nome)
	{
		try 
		{
			Turno turno = null;
			Integer numeroArqumentos = 1;
			String querySQL = "SELECT * FROM TURNO"
					+ " WHERE STATUS = ?";
			
			if(pkTurno!=null)
			{
				querySQL += " AND PKTURNO = ?";
			}
			if(nome!=null && nome.length() >0)
			{
				querySQL+= " AND DESCRICAO LIKE ?";
			}
			querySQL+= " ORDER BY DESCRICAO";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(numeroArqumentos, ConstantesSisEducar.STATUS_ATIVO);
			if(pkTurno!=null)
			{
				numeroArqumentos ++;
				ps.setInt(numeroArqumentos, pkTurno);
			}
			if(nome!=null && nome.length() >0)
			{
				numeroArqumentos ++;
				ps.setObject(numeroArqumentos, "%" + nome + "%");
			}
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				turno = new Turno();
				turno.setPkTurno(pkTurno);
				turno.setDescricao(rs.getString("DESCRICAO"));
			}
			
			return turno;
		} 
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
}
