package modulos.sisEducar.conexaoBanco;

import java.sql.Connection;

import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;


public class ConectaBanco {
	
	public Connection getConection() {
		
		try {
			InitialContext cxt = new InitialContext ();
			if (cxt == null) {
			   System.out.println("Uh oh - sem contexto!");
			}

			DataSource ds = (DataSource) cxt.lookup ("java:/comp/env/jdbc/coruja");

			if ( ds == null ) {
			   System.out.println("Fonte de dados não encontrada!");
			}
			
			/*
			InitialContext initCtx = new InitialContext();
			
			Context envContext  = (Context)initCtx.lookup("java:/comp/env");
			
			DataSource ds = (DataSource)envContext.lookup("jdbc/coruja");
			
			//DataSource ds = (DataSource) initCtx.lookup( "java:/comp/env/jdbc/coruja" );
			Connection con = ds.getConnection();*/
			
			return null;
		} catch (Exception e) {
			
			return null;
		}
	}
}

