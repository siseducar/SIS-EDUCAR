package modulos.sisEducar.conexaoBanco;

public class ParametrosBD 
{
	String DRIVER = "org.postgresql.Driver";
	String URL = "jdbc:postgresql://localhost:5432/sisEducar";
	String usuario = "postgres";
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