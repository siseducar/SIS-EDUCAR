package modulos.sisEducar.conexaoBanco;

public class ParametrosBD 
{
	String nomeBaseOriginal = "sisEducar";
	String nomeBaseTeste = "postgres";
	
	String DRIVER = "org.postgresql.Driver";
	String URL = "jdbc:postgresql://10.230.20.176/" + nomeBaseTeste;
	String usuario = "webadmin";
	String senha = "postgres";

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
