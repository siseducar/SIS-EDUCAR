package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import modulos.secretaria.dao.HistoricoAcessoDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.HistoricoAcesso;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.utils.ConstantesSecretaria;

@ManagedBean(name="historicoAcessoServlet")
@ViewScoped
public class HistoricoAcessoServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    private List<HistoricoAcesso> acessos;
	private Usuario usuarioLogado = null;
	private Boolean btConsultaEnabled = false;
	public Boolean temPermissaoConsultar = false;
	private String cpfPessoa = "";
	private String nomePessoa = "";
	private Pessoa pessoaBusca = null;
	
	/**
	 * Construtor
	 */
	public HistoricoAcessoServlet()
	{	
		btConsultaEnabled = false;
		
		//Busco o usuário logado
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		setCpfPessoa("");
		setNomePessoa("");
		validarPermissoes();
		if(temPermissaoConsultar) { btConsultaEnabled = true; }
	}
	
	public void validarPermissoes()
	{
		//validar permissão
		for (Permissao permissao : usuarioLogado.getPermissoes()) 
		{
			if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CONSULTAR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CONSULTAS_HISTORICO_ACESSO))
			{
				temPermissaoConsultar = true;
			}
		}
	}
	
	public void consultarAcessos() throws SQLException
	{
		acessos = new HistoricoAcessoDAO().consultar(null, pessoaBusca, null, null);
	}
	
	public void buscarPessoa() throws SQLException
	{
		pessoaBusca = new Pessoa();
		if(cpfPessoa!=null && cpfPessoa.length()>0)
		{
			pessoaBusca = new PessoaDAO().obtemUnicoPessoaSimples(cpfPessoa);
//			nomePessoa = "";
			if(pessoaBusca!=null)
			{
				nomePessoa = pessoaBusca.getNome();
			}
		}
	}
	
	private HtmlDataTable dataTable;
	   
    public HtmlDataTable getDataTable() {
          return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
          this.dataTable = dataTable;
    }            

	public void pageFirst() {
        dataTable.setFirst(0);
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
	
    public int getCurrentPage() {
        int rows = dataTable.getRows();
        int first = dataTable.getFirst();
        int count = dataTable.getRowCount();
        return (count / rows) - ((count - first) / rows) + 1;
    }

    public int getTotalPages() {
        int rows = dataTable.getRows();
        int count = dataTable.getRowCount();
        return (count / rows) + ((count % rows != 0) ? 1 : 0);
    }


	public List<HistoricoAcesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<HistoricoAcesso> acessos) {
		this.acessos = acessos;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getBtConsultaEnabled() {
		return btConsultaEnabled;
	}

	public void setBtConsultaEnabled(Boolean btConsultaEnabled) {
		this.btConsultaEnabled = btConsultaEnabled;
	}

	public String getCpfPessoa() {
		return cpfPessoa;
	}

	public void setCpfPessoa(String cpfPessoa) {
		this.cpfPessoa = cpfPessoa;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Pessoa getPessoaBusca() {
		return pessoaBusca;
	}

	public void setPessoaBusca(Pessoa pessoaBusca) {
		this.pessoaBusca = pessoaBusca;
	}
}
