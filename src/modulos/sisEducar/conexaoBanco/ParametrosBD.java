package modulos.sisEducar.conexaoBanco;

public class ParametrosBD 
{
	String nomeBaseOriginal = "sisEducar";
	String nomeBaseTeste = "sisEducarTeste";
	String nomeServidor = "coruja";
	
	//Dados referente ao banco
//	String DRIVER = "org.postgresql.Driver";
//	String URL = "jdbc:postgresql://10.70.26.198:5432/" + nomeServidor;
//	String usuario = "webadmin";
//	String senha = "LGRgza99444";
	
	/* Dados referente ao banco
	String DRIVER = "org.postgresql.Driver";
<<<<<<< HEAD
	String URL = "jdbc:postgresql://10.70.26.198:5432/" + nomeServidor;
	String usuario = "webadmin";
	String senha = "LGRgza99444";
	 */
	
	String DRIVER = "org.postgresql.Driver";
	String URL = "jdbc:postgresql://localhost:5432/" + nomeBaseOriginal;
=======
	String URL = "jdbc:postgresql://localhost:5432/" + nomeBaseTeste;
>>>>>>> branch 'master' of https://github.com/siseducar/SIS-EDUCAR.git
	String usuario = "postgres";
	String senha = "postgres";
<<<<<<< HEAD
	
=======
>>>>>>> branch 'master' of https://github.com/siseducar/SIS-EDUCAR.git
	
	/* Getters and Setters */
	public String getDRIVER() {	return DRIVER; }
	public void setDRIVER(String dRIVER) { DRIVER = dRIVER; }

	public String getURL() { return URL; }
	public void setURL(String uRL) { URL = uRL; }

	public String getUsuario() { return usuario; }
	public void setUsuario(String usuario) { this.usuario = usuario; }

	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }
}
