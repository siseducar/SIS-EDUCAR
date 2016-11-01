package modulos.secretaria.servlet;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import modulos.secretaria.om.HistoricoAcesso;
import modulos.secretaria.om.Permissao;
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
	private String nomeUsuario = "";
	
	/**
	 * Construtor
	 */
	public HistoricoAcessoServlet()
	{	
		btConsultaEnabled = false;
		
		//Busco o usuário logado
		usuarioLogado = (Usuario)  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		nomeUsuario = usuarioLogado.getNome();
		
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
	
	public void consultarAcessos()
	{
	}
	
	private HtmlDataTable dataTable;
	private HtmlDataTable dataTablePermissao;
	   
    public HtmlDataTable getDataTable() {
          return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
          this.dataTable = dataTable;
    }            

    public HtmlDataTable getDataTablePermissao() {
		return dataTablePermissao;
	}

	public void setDataTablePermissao(HtmlDataTable dataTablePermissao) {
		this.dataTablePermissao = dataTablePermissao;
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

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
}
