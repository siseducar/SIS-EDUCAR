package modulos.RH.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import modulos.RH.om.ParametrosSecretaria;
import modulos.sisEducar.conexaoBanco.ConectaBanco;

public class ParametrosDAO {

	// Realizando conexão com o banco
		ConectaBanco conexao = new ConectaBanco();
		Connection con = conexao.getConection();
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		/* Metodo para retornar os tipos de cores padrao */
		public List<ParametrosSecretaria> consultaRaca() throws SQLException{
			
			List<ParametrosSecretaria> listaRaca = new ArrayList<ParametrosSecretaria>();
			
			String querySQL = "SELECT * FROM RACA ORDER BY PKRACA";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while (rs.next()){
				ParametrosSecretaria paramCor = new ParametrosSecretaria();
				paramCor.setPkCor(rs.getInt("PKRACA"));
				paramCor.setCodCor(rs.getString("CODIGO"));
				paramCor.setDescriCor(rs.getString("DESCRICAO"));
				
				listaRaca.add(paramCor);
			}
			
			return listaRaca;
		}
		
		/* Metodo para retornar os tipos de ESTADO CIVIL padrao */
		public List<ParametrosSecretaria> consultaEstaCivil() throws SQLException{
			
			List<ParametrosSecretaria> listaEstaCivil = new ArrayList<>();
			
			String querySQL = "SELECT * FROM ESTADOCIVIL ORDER BY DESCRICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while(rs.next()){
				ParametrosSecretaria paramEstCivil = new ParametrosSecretaria();
				paramEstCivil.setPkCodEstCivil(rs.getInt("PKESTADOCIVIL"));
				paramEstCivil.setCodEstadoCivil(rs.getString("CODIGO"));
				paramEstCivil.setDescriEstadoCivil(rs.getString("DESCRICAO"));
				
				listaEstaCivil.add(paramEstCivil);
			}
			
			return listaEstaCivil;
		}
		
		/* Metodo para retornar os tipos de GRAU DE INSTRUCAO padrao */
		public List<ParametrosSecretaria> consultaGrauInstru() throws SQLException {
			
			List<ParametrosSecretaria> listaGrauInstru = new ArrayList<>();
			
			String querySQL = "SELECT * FROM GRAUINSTRUCAO ORDER BY PKGRAUINSTRUCAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while(rs.next()) {
				ParametrosSecretaria paramGrauInstru = new ParametrosSecretaria();
				paramGrauInstru.setPkGrauInstru(rs.getInt("PKGRAUINSTRUCAO"));
				paramGrauInstru.setCodGrauInstrucao(rs.getString("CODIGO"));
				paramGrauInstru.setDescriGrauIntrucao(rs.getString("DESCRICAO"));
				
				listaGrauInstru.add(paramGrauInstru);
			}
			
			return listaGrauInstru;
		}
		
		/* Metodo para retornar os tipos de NACIONALIDADE padrao */
		public List<ParametrosSecretaria> consultaNacionalidade() throws SQLException {
			
			List<ParametrosSecretaria> listaNacionalidade = new ArrayList<>();
			
			String querySQL = "SELECT * FROM GRAUINSTRUCAO ORDER BY DESCRICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while(rs.next()) {
				ParametrosSecretaria paramNacionalidade = new ParametrosSecretaria();
				paramNacionalidade.setPkNacionalidade(rs.getInt("PKNACIONALIDADE"));
				paramNacionalidade.setCodNacionalidade(rs.getString("CODIGO"));
				paramNacionalidade.setDescriNacionalidade(rs.getString("DESCRICAO"));
				
				listaNacionalidade.add(paramNacionalidade);
			}
			
			return listaNacionalidade;
		}
		
		/* Metodo para retornar os tipos de RELIGIAO padrao */
		public List<ParametrosSecretaria> consultaReligiao() throws SQLException {
			
			List<ParametrosSecretaria> listaReligiao = new ArrayList<>();
			
			String querySQL = "SELECT * FROM RELIGIAO ORDER BY DESCRICAO";
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery(querySQL);
			
			while(rs.next()) {
				ParametrosSecretaria paramReligiao = new ParametrosSecretaria();
				paramReligiao.setPkReligiao(rs.getInt("PKRELIGIAO"));
				paramReligiao.setCodReligiao(rs.getString("CODIGO"));
				paramReligiao.setDescriReligiao(rs.getString("DESCRICAO"));
				
				listaReligiao.add(paramReligiao);
			}
			
			return listaReligiao;
		}
}
