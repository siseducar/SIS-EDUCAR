package modulos.sisEducar.conexaoBanco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBanco {
	
	public Connection getConection() {
		ParametrosBD conf = new ParametrosBD();
		Connection con = null;

		try {
			Class.forName(conf.getDRIVER());
			try {
				con = DriverManager.getConnection(conf.getURL(), conf.getUsuario(), conf.getSenha());
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}
}

