package modulos.educacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForEach;

import modulos.educacao.om.Horario;
import modulos.educacao.om.HorarioAula;
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

	public HorarioDAO() throws SQLException 
	{
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
	public Horario obtemHorariosPorTurno(UnidadeEscolar unidadeEscolar, Turno turno) throws SQLException
	{
		Integer numeroArgumentos = 1;
		Horario horario = null;
		
		String querySQL = "SELECT * FROM HORARIO"
				+ " WHERE STATUS = ?";
		
		if(unidadeEscolar!=null)		 	{ querySQL += " AND FKUNIDADEESCOLAR = ?"; }
		if(turno!=null)						{ querySQL += " AND FKTURNO = ?"; }
		
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
			horario.setTurno(turno);
			horario.setUnidadeEscolar(unidadeEscolar);
			
			horario.setHorariosAula(obtemHorariosAula(horario));
		}
		
		return horario;
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
					+ " (horaInicio, minutoInicio, horaTermino, minutoTermino, horaIntervalo, minutoIntervalo, horaHoraAula, minutoHoraAula, status, fkUnidadeEscolar, fkTurno) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?) RETURNING pkHorario";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setDouble(1, horario.getHoraInicio());
			ps.setDouble(2, horario.getMinutoInicio());
			ps.setDouble(3, horario.getHoraTermino());
			ps.setDouble(4, horario.getMinutoTermino());
			ps.setDouble(5, horario.getHoraIntervalo());
			ps.setDouble(6, horario.getMinutoIntervalo());
			ps.setDouble(7, horario.getHoraHoraAula());
			ps.setDouble(8, horario.getMinutoHoraAula());
			ps.setInt(9, ConstantesSisEducar.STATUS_INCOMPLETO);
			ps.setObject(10, horario.getUnidadeEscolar().getPkUnidadeEscolar());
			ps.setObject(11, horario.getTurno().getPkTurno());
			
			ResultSet rs = ps.executeQuery();
			horario.setPkHorario(rs.getInt("pkHorario"));
			
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
					+ " (horaInicio, minutoInicio, horaTermino, minutoTermino, status, fkHorario) "
					+ " values(?,?,?,?,?,?)";
			
			ps = con.prepareStatement(querySQL);
			
			ps.setDouble(1, horarioAula.getHoraInicio());
			ps.setDouble(2, horarioAula.getMinutoInicio());
			ps.setDouble(3, horarioAula.getHoraTermino());
			ps.setDouble(4, horarioAula.getMinutoTermino());
			ps.setInt(5, ConstantesSisEducar.STATUS_INCOMPLETO);
			ps.setDouble(6, horarioAula.getHorario().getPkHorario());
			
			fecharConexaoBanco(con, ps, false, true);
		} 
		catch (SQLException e) 
		{
			System.out.println(e);
		}
	}
}
