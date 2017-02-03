package modulos.sisEducar.conexaoBanco;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;


public class ConectaBanco {
	
	public Connection getConection() {
		
		try {
			
			InitialContext initCtx = new InitialContext();
			
			Context envContext  = (Context)initCtx.lookup("java:/comp/env");
			
			DataSource ds = (DataSource)envContext.lookup("jdbc/coruja");
			
			//DataSource ds = (DataSource) initCtx.lookup( "java:/comp/env/jdbc/coruja" );
			Connection con = ds.getConnection();
			
			return con;
		} catch (SQLException | NamingException e) {
			
			return null;
		}
	}
}

