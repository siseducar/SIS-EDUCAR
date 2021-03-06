package modulos.educacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import modulos.educacao.om.Horario;
import modulos.educacao.om.HorarioAula;
import modulos.secretaria.dao.TurnoDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;
import modulos.secretaria.om.Usuario;
import modulos.sisEducar.conexaoBanco.ConectaBanco;
import modulos.sisEducar.dao.SisEducarDAO;
import modulos.sisEducar.utils.ConstantesSisEducar;

public class HorarioDAO extends SisEducarDAO 
{
	// Realizando conexão com o banco
	ConectaBanco conexao = new ConectaBanco();
	Connection con = conexao.getConection();
	Statement st = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	Usuario usuarioLogado = null;

	public HorarioDAO() throws SQLException 
	{
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		desabilitarAutoCommit(con);
	}
	
	/**
	 * busca um horário com seus determinado horários de aula
	 * @author João Paulo
	 * @param unidadeEscolar
	 * @param turno
	 * @return
	 * @throws SQLException
	 */
	public Horario obtemHorariosPorTurno(UnidadeEscolar unidadeEscolar, Turno turno, Integer pkHorario) throws SQLException
	{
		Integer numeroArgumentos = 1;
		Horario horario = null;
		
		String querySQL = "SELECT * FROM HORARIO"
				+ " WHERE STATUS = ?";
		
		if(unidadeEscolar!=null)		 	{ querySQL += " AND FKUNIDADEESCOLAR = ?"; }
		if(turno!=null)						{ querySQL += " AND FKTURNO = ?"; }
		if(pkHorario!=null)						{ querySQL += " AND PKHORARIO = ?"; }
		
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(unidadeEscolar!=null)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, unidadeEscolar.getPkUnidadeEscolar());
		}
		
		if(turno!=null)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, turno.getPkTurno());
		}
		
		if(pkHorario!=null)
		{
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, pkHorario);
		}
				
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			horario = new Horario();
			horario.setPkHorario(rs.getInt("PKHORARIO"));
			horario.setHoraInicio(rs.getDouble("HORAINICIO"));
			horario.setMinutoInicio(rs.getDouble("MINUTOINICIO"));
			horario.setHoraTermino(rs.getDouble("HORATERMINO"));
			horario.setMinutoTermino(rs.getDouble("MINUTOTERMINO"));
			horario.setHoraIntervalo(rs.getDouble("HORAINTERVALO"));
			horario.setMinutoIntervalo(rs.getDouble("MINUTOINTERVALO"));
			horario.setHoraHoraAula(rs.getDouble("HORAHORAAULA"));
			horario.setMinutoHoraAula(rs.getDouble("MINUTOHORAAULA"));
			horario.setNome(rs.getString("NOME"));
			horario.setTurno(new TurnoDAO().obtemSimples(rs.getInt("FKTURNO"), null));
			horario.setUnidadeEscolar(new UnidadeEscolarDAO().buscarUnidadeEscolarSimples(null, null, rs.getInt("FKUNIDADEESCOLAR")));
			
			horario.setHorariosAula(obtemHorariosAula(horario));
		}
		
		return horario;
	}
	
	/**
	 * Busca todos os horários com status ativo no banco de dados
	 * @author João Paulo
	 * @param nome
	 * @param unidadeEscolar
	 * @param turno
	 * @return
	 * @throws SQLException
	 */
	public List<Horario> consultarSimples(String nome, UnidadeEscolar unidadeEscolar, Turno turno) throws SQLException
	{
		Integer numeroArgumentos = 1;
		Horario horario = null;
		List<Horario> horarios = new ArrayList<Horario>();
		
		String querySQL = "SELECT H.* FROM HORARIO H"
				+ " INNER JOIN UNIDADEESCOLAR U ON(H.FKUNIDADEESCOLAR = U.PKUNIDADEESCOLAR)"
				+ " WHERE H.STATUS = ?";
		
		if(nome!=null && nome.length()>0)		 								{ querySQL += " AND UPPER(H.NOME) LIKE UPPER('%" + nome + "%')"; }
		if(unidadeEscolar!=null && unidadeEscolar.getPkUnidadeEscolar()!=null)	{ querySQL += " AND U.PKUNIDADEESCOLAR = ?"; }
		if(turno!=null && turno.getPkTurno()!=null)								{ querySQL += " AND H.FKTURNO = ?"; }
		
		querySQL += " AND U.FKMUNICIPIOCLIENTE = ?";
		ps = con.prepareStatement(querySQL);
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(unidadeEscolar!=null && unidadeEscolar.getPkUnidadeEscolar()!=null)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, unidadeEscolar.getPkUnidadeEscolar());
		}
		
		if(turno!=null && turno.getPkTurno()!=null)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, turno.getPkTurno());
		}
		
		numeroArgumentos++;
		ps.setObject(numeroArgumentos, usuarioLogado.getFkMunicipioCliente().getPkCidade());
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			horario = new Horario();
			horario.setPkHorario(rs.getInt("PKHORARIO"));
			horario.setNome(rs.getString("NOME"));
			horario.setTurno(new TurnoDAO().obtemSimples(rs.getInt("FKTURNO"), null));
			horario.setUnidadeEscolar(new UnidadeEscolarDAO().buscarUnidadeEscolarSimples(null, null, rs.getInt("FKUNIDADEESCOLAR")));
			
			horarios.add(horario);
		}
		
		return horarios;
	}
	
	/**
	 * Busca todos as horários aula de um determinado horário
	 * @author João Paulo
	 * @param horario
	 * @return
	 * @throws SQLException
	 */
	public List<HorarioAula> obtemHorariosAula(Horario horario) throws SQLException
	{
		Integer numeroArgumentos = 1;
		HorarioAula horarioAula = null;
		List<HorarioAula> horariosAula = new ArrayList<HorarioAula>();
		
		String querySQL = "SELECT * FROM HORARIOAULA"
				+ " WHERE STATUS = ?";
		
		if(horario!=null)		 	{ querySQL += " AND FKHORARIO = ?"; }
		
		querySQL += " ORDER BY PKHORARIOAULA";
		ps = con.prepareStatement(querySQL);
		
		ps.setInt(numeroArgumentos, ConstantesSisEducar.STATUS_ATIVO);
		
		if(horario!=null)	
		{ 
			numeroArgumentos ++; 
			ps.setInt(numeroArgumentos, horario.getPkHorario());
		}
		
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			horarioAula = new HorarioAula();
			horarioAula.setPkHorarioAula(rs.getInt("PKHORARIOAULA"));
			horarioAula.setHoraInicio(rs.getDouble("HORAINICIO"));
			horarioAula.setMinutoInicio(rs.getDouble("MINUTOINICIO"));
			horarioAula.setHoraTermino(rs.getDouble("HORATERMINO"));
			horarioAula.setMinutoTermino(rs.getDouble("MINUTOTERMINO"));
			horarioAula.setTipoIntervalo(rs.getBoolean("TIPOINTERVALO"));
			
			horariosAula.add(horarioAula);
		}
		
		return horariosAula;
	}
	
	/**
	 * Usado para inserir um horario no banco de dados
	 * @author João Paulo
	 * @param horario
	 * @return
	 */
	public Boolean inserirHorario(Horario horario)
	{
		try 
		{
			String querySQL = "INSERT INTO horario "
					+ " (horaInicio, minutoInicio, horaTermino, minutoTermino, horaIntervalo, minutoIntervalo, horaHoraAula, minutoHoraAula, status, fkUnidadeEscolar, fkTurno, nome) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?) RETURNING PKHORARIO";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setDouble(1, horario.getHoraInicio());
			ps.setDouble(2, horario.getMinutoInicio());
			ps.setDouble(3, horario.getHoraTermino());
			ps.setDouble(4, horario.getMinutoTermino());
			ps.setDouble(5, horario.getHoraIntervalo());
			ps.setDouble(6, horario.getMinutoIntervalo());
			ps.setDouble(7, horario.getHoraHoraAula());
			ps.setDouble(8, horario.getMinutoHoraAula());
			ps.setInt(9, ConstantesSisEducar.STATUS_ATIVO);
			ps.setObject(10, horario.getUnidadeEscolar().getPkUnidadeEscolar());
			ps.setObject(11, horario.getTurno().getPkTurno());
			ps.setObject(12, horario.getNome());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				horario.setPkHorario(rs.getInt("PKHORARIO"));
			}
			
			fecharConexaoBanco(con, ps, false, true);
			deleteNotIn(horario);
			fecharConexaoBanco(con, ps, false, true);
			
			for (HorarioAula ha : horario.getHorariosAula()) 
			{
				ha.setHorario(horario);
				inserirHorarioAula(ha);
			}
			
			return true;
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Usado para inserir um horario aula no banco de dados
	 * @author João Paulo
	 * @param horarioAula
	 */
	public void inserirHorarioAula(HorarioAula horarioAula)
	{
		try 
		{
			String querySQL = "INSERT INTO horarioAula "
					+ " (horaInicio, minutoInicio, horaTermino, minutoTermino, status, fkHorario, tipoIntervalo) "
					+ " values(?,?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setDouble(1, horarioAula.getHoraInicio());
			ps.setDouble(2, horarioAula.getMinutoInicio());
			ps.setDouble(3, horarioAula.getHoraTermino());
			ps.setDouble(4, horarioAula.getMinutoTermino());
			ps.setInt(5, ConstantesSisEducar.STATUS_ATIVO);
			ps.setDouble(6, horarioAula.getHorario().getPkHorario());
			ps.setBoolean(7, horarioAula.getTipoIntervalo());
			
			fecharConexaoBanco(con, ps, false, true);
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Deleta todos os horários de aula de um horário
	 * @author João Paulo
	 * @param horario
	 * @return
	 */
	public Boolean deletarHorariosAula(Horario horario) 
	{
		try 
		{
			String querySQL = "UPDATE HORARIOAULA "
					+ " SET STATUS = ?"
					+ " WHERE FKHORARIO = ? RETURNING STATUS";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, horario.getPkHorario());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) 
				{
					fecharConexaoBanco(con, ps, false, true);
					return true;
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	/**
	 * Usado para deletar um horário do sistema e as aulas de horário
	 * @param horario
	 * @return
	 */
	public Boolean deletarHorario(Horario horario) 
	{
		try 
		{
			String querySQL = "UPDATE HORARIO "
					+ " SET STATUS = ?"
					+ " WHERE PKHORARIO = ? RETURNING STATUS";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, horario.getPkHorario());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("STATUS") == ConstantesSisEducar.STATUS_REMOVIDO) 
				{
					fecharConexaoBanco(con, ps, false, true);
					
					for (HorarioAula horarioAula : horario.getHorariosAula()) 
					{
						deletarHorariosAula(horario);
					}
					
					return true;
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	/**
	 * Usado para editar um horário
	 * @author João Paulo
	 * @param horario
	 * @return
	 */
	public Boolean editarHorario(Horario horario) 
	{
		try 
		{
			String querySQL = "UPDATE HORARIO "
					+ " SET (HORAINICIO, MINUTOINICIO, HORATERMINO, MINUTOTERMINO, HORAINTERVALO, MINUTOINTERVALO, HORAHORAAULA, MINUTOHORAAULA, FKUNIDADEESCOLAR, FKTURNO, NOME) "
					+ " = (?,?,?,?,?,?,?,?,?,?,?)"
					+ " WHERE PKHORARIO = ? RETURNING PKHORARIO";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setDouble(1, horario.getHoraInicio());
			ps.setDouble(2, horario.getMinutoInicio());
			ps.setDouble(3, horario.getHoraTermino());
			ps.setDouble(4, horario.getMinutoTermino());
			ps.setDouble(5, horario.getHoraIntervalo());
			ps.setDouble(6, horario.getMinutoIntervalo());
			ps.setDouble(7, horario.getHoraHoraAula());
			ps.setDouble(8, horario.getMinutoHoraAula());
			ps.setObject(9, horario.getUnidadeEscolar().getPkUnidadeEscolar());
			ps.setObject(10, horario.getTurno().getPkTurno());
			ps.setObject(11, horario.getNome());
			ps.setObject(12, horario.getPkHorario());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("PKHORARIO") > 0) 
				{
					fecharConexaoBanco(con, ps, false, true);
					for (HorarioAula ha : horario.getHorariosAula()) 
					{
						ha.setHorario(horario);
						inserirHorarioAula(ha);
					}
					
					return true;
				}
			}
			
			return false;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	public Boolean deleteNotIn(Horario horario) 
	{
		try 
		{
			String querySQL = "UPDATE HORARIO SET STATUS = ? WHERE PKHORARIO <> ? AND FKTURNO = ? AND FKUNIDADEESCOLAR = ?";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setInt(1, ConstantesSisEducar.STATUS_REMOVIDO);
			ps.setInt(2, horario.getPkHorario());
			ps.setInt(3, horario.getTurno().getPkTurno());
			ps.setInt(4, horario.getUnidadeEscolar().getPkUnidadeEscolar());

			ps.executeQuery();
			
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
}
