package modulos.educacao.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modulos.educacao.om.Horario;
import modulos.educacao.om.HorarioAula;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;
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
}
