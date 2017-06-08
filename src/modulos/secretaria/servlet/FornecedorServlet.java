package modulos.secretaria.servlet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.TipoLogradouro;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.services.ContatoService;
import modulos.secretaria.services.EnderecoService;
import modulos.secretaria.services.FornecedorService;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="forneServlet")
@ViewScoped
public class FornecedorServlet implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{paramServlet}")
	private ParametrosServlet paramDados;

	private Fornecedor fornecedorDados;
	private Endereco enderecoDados;
	private Contato contatoDados;
	private Pessoa pessoaDados;
	
	private Estado estadoInscricaoDados;
	private Cidade cidadeInscricaoDados;
	private Pais paisDados;
	private Estado estadoDados;
	private Cidade cidadeDados;
	private TipoLogradouro tipoLograDados;
	private Regiao regiaoDados;
	
	private Usuario usuarioLogadao;
		
	/* Combo com os valores de ESTADO */
	private List<SelectItem> comboEstado;
	
	/* Combo com os valores de MUNICIPIO */
	private List<SelectItem> comboMunicipio;
	
	/* Combo com os valores de MUNICIPIO */
	private List<SelectItem> comboMunicipioInscricao;
	
	private List<Fornecedor> listaConsultaFornecedores;
	
	/* Componenete Tabela de consulta de Cadastros*/
	private HtmlDataTable dataTable;
	
	private Boolean panelDadosEmpresa;
	
	@PostConstruct
	public void init() {
		if( this.fornecedorDados == null ) {
			this.fornecedorDados = new Fornecedor();
		}
		if( this.enderecoDados == null ) {
			this.enderecoDados = new Endereco();
			this.getEnderecoDados().setTipologradouro(new TipoLogradouro());
		}
		if( this.contatoDados == null ) {
			this.contatoDados = new Contato();
		}
		if( this.estadoInscricaoDados == null ) {
			this.estadoInscricaoDados = new Estado();
		}
		if( this.cidadeInscricaoDados == null ) {
			this.cidadeInscricaoDados = new Cidade();
		}
		if( this.paisDados == null ) {
			this.paisDados = new Pais();
		}
		if( this.estadoDados == null ) {
			this.estadoDados = new Estado();
		}
		if( this.cidadeDados == null ) {
			this.cidadeDados = new Cidade();
		}
		if( this.tipoLograDados == null ) {
			this.tipoLograDados = new TipoLogradouro();
		}
		if( this.regiaoDados == null ) {
			this.regiaoDados = new Regiao();
		}
		if( this.pessoaDados == null ) {
			this.pessoaDados = new Pessoa();
		}
		if(this.dataTable == null) {
			this.dataTable = new HtmlDataTable();
		}
		
		comboEstado = new ArrayList<SelectItem>();
		comboMunicipio = new ArrayList<SelectItem>();
		comboMunicipioInscricao = new ArrayList<SelectItem>();
		
		panelDadosEmpresa = false;
		
		usuarioLogadao = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		pessoaDados.setFkMunicipioCliente(usuarioLogadao.getFkMunicipioCliente());
	}
	
	public void validaCadastro() {
		if( validaDados() ) {
			if(fornecedorDados.getPkFornecedor() == null ) {
				salvarCadastroFornecedor();
			} else {
				
			}
			
		}
	}
	
	public void salvarCadastroFornecedor() {
		enderecoDados.setTipo("Comercial");
		enderecoDados.setFkMunicipioCliente(usuarioLogadao.getFkMunicipioCliente());
		
		if( !new ContatoService().salvarContato(contatoDados)) {
			Logs.addError("Erro ao salvar contato.", null);
		} else {
			enderecoDados.setContato(contatoDados);
			if( !new EnderecoService().salvarDadosEndereco(enderecoDados)) {
				Logs.addError("Erro ao salvar endereco.", null);
			} else {
				fornecedorDados.setEstadoInscricao(estadoInscricaoDados);
				fornecedorDados.setCidadeInscricao(cidadeInscricaoDados);
				fornecedorDados.setFkMunicipioCliente(usuarioLogadao.getFkMunicipioCliente());
				fornecedorDados.setEndereco(enderecoDados);
				fornecedorDados.setPessoa(pessoaDados);
				if( !new FornecedorService().salvarCadastroFornecedor(fornecedorDados)) {
					Logs.addError("Erro ao salvar Fornecedor.", null);
				} else {
					Logs.addInfo("Fornecedor cadastrado com sucesso", null);
				}
			}
		}
	}
	
	
	public Boolean validaDados() {
		if(fornecedorDados.getRazaoSocial() == null || fornecedorDados.getRazaoSocial().equals("") ) {
			Logs.addWarning("A RAZÃO SOCIAL deve ser preenchida.", null);
			return false;
		}
		
		if( fornecedorDados.getNomeFantasia() == null || fornecedorDados.getNomeFantasia().equals("") ) {
			Logs.addWarning("O NOME FANTASIA deve ser preenchida.", null);
			return false;
		}
		
		if( fornecedorDados.getCnpj() == null || fornecedorDados.getCnpj().equals("") ) {
			Logs.addWarning("O CNPJ deve ser preenchida.", null);
			return false;
		}
		
		if( paisDados.getPkPais() == null ) {
			Logs.addWarning("O PAÍS deve ser informado.", null);
			return false;
		} 
		
		if( estadoDados.getPkEstado() == null ) {
			Logs.addWarning("O ESTADO deve ser informado.", null);
			return false;
		}
		
		if( cidadeDados.getPkCidade() == null ) {
			Logs.addWarning("O MUNICÍPIO deve ser informado.", null);
			return false;
		} else {
			enderecoDados.setCidade(cidadeDados);
			enderecoDados.getCidade().setEstado(estadoDados);
			enderecoDados.getCidade().getEstado().setPais(paisDados);
		}
		
		if( enderecoDados.getCep() == null || enderecoDados.getCep().equals("")) {
			Logs.addWarning("O CEP deve ser preenchido.", null);
			return false;
		}
		
		if( tipoLograDados.getPkTipoLogradouro() == null ) {
			Logs.addWarning("O TIPO DE LOGRADOURO deve ser preenchido.", null);
			return false;
		} else {
			enderecoDados.setTipologradouro(tipoLograDados);
		}
		
		if( enderecoDados.getLogradouro() == null || enderecoDados.getCep().equals("")) {
			Logs.addWarning("O LOGRADOURO deve ser preenchido.", null);
			return false;
		}
		
		if( enderecoDados.getNumero() == null || enderecoDados.getNumero().equals("")) {
			Logs.addWarning("O NÚMERO deve ser preenchido.", null);
			return false;
		}
		
		if( enderecoDados.getBairro() == null || enderecoDados.getBairro().equals("")) {
			Logs.addWarning("O BAIRRO deve ser preenchido.", null);
			return false;
		}
		
		if( regiaoDados.getPkRegiao() == null ) {
			Logs.addWarning("A ZONA RESIDENCIAL deve ser informada.", null);
			return false;
		} else {
			enderecoDados.setRegiao(regiaoDados);
		}
		
		if( contatoDados.getEmailempresa() == null || contatoDados.getEmailempresa().equals("")) {
			Logs.addWarning("O EMAIL deve ser informado.", null);
			return false;
		}
		
		if( contatoDados.getSiteempresa() == null || contatoDados.getSiteempresa().equals("")) {
			Logs.addWarning("O SITE deve ser informado.", null);
			return false;
		}
		
		if( contatoDados.getTelComercial() == null || contatoDados.getTelComercial().equals("")) {
			Logs.addWarning("O TELEFONE deve ser informado.", null);
			return false;
		}
		
		if( contatoDados.getFax() == null || contatoDados.getFax().equals("")) {
			Logs.addWarning("O FAX deve ser informado.", null);
			return false;
		}
		
		return true;
	}
	
	
	public void consultaNomeContato() {
		try {
			if( pessoaDados.getCpf() != null && pessoaDados.getCpf() > 0) {				
				
				pessoaDados = new PessoaDAO().obtemUnicoPessoaSimples(pessoaDados.getCpf().toString());
				if( pessoaDados.getNome() != null ) {
					panelDadosEmpresa = true;
				} else {
					pessoaDados.setCpf(null);
					pessoaDados.setNome(null);
					panelDadosEmpresa = false;
					Logs.addInfo("CPF não encontrado!", null);
				}
			} else {
				pessoaDados.setCpf(null);
				pessoaDados.setNome(null);
				panelDadosEmpresa = false;
			}
		} catch (Exception e) {
		}
	}
	
	public void consultaCadastro() {
		
		listaConsultaFornecedores = new ArrayList<Fornecedor>();
		listaConsultaFornecedores = new FornecedorService().consultaFornecedores();
	}
	
	/* 
	 * Tabela de consulta de cadastro de Pessoa 
	 * * * * * * */
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
    
    
	/* GETTERS AND SETTERS */
	public ParametrosServlet getParamDados() {
		return paramDados;
	}

	public void setParamDados(ParametrosServlet paramDados) {
		this.paramDados = paramDados;
	}

	public Fornecedor getFornecedorDados() {
		return fornecedorDados;
	}

	public void setFornecedorDados(Fornecedor fornecedorDados) {
		this.fornecedorDados = fornecedorDados;
	}

	public Endereco getEnderecoDados() {
		return enderecoDados;
	}


	public void setEnderecoDados(Endereco enderecoDados) {
		this.enderecoDados = enderecoDados;
	}


	public Contato getContatoDados() {
		return contatoDados;
	}


	public void setContatoDados(Contato contatoDados) {
		this.contatoDados = contatoDados;
	}


	public Pessoa getPessoaDados() {
		return pessoaDados;
	}

	public void setPessoaDados(Pessoa pessoaDados) {
		this.pessoaDados = pessoaDados;
	}

	public Estado getEstadoInscricaoDados() {
		return estadoInscricaoDados;
	}


	public void setEstadoInscricaoDados(Estado estadoInscricaoDados) {
		this.estadoInscricaoDados = estadoInscricaoDados;
	}


	public Cidade getCidadeInscricaoDados() {
		return cidadeInscricaoDados;
	}


	public void setCidadeInscricaoDados(Cidade cidadeInscricaoDados) {
		this.cidadeInscricaoDados = cidadeInscricaoDados;
	}


	public Pais getPaisDados() {
		return paisDados;
	}

	public void setPaisDados(Pais paisDados) {
		this.paisDados = paisDados;
	}

	public Estado getEstadoDados() {
		return estadoDados;
	}

	public void setEstadoDados(Estado estadoDados) {
		this.estadoDados = estadoDados;
	}

	public Cidade getCidadeDados() {
		return cidadeDados;
	}

	public void setCidadeDados(Cidade cidadeDados) {
		this.cidadeDados = cidadeDados;
	}
	
	public List<SelectItem> getComboEstado() {
		if(paisDados.getPkPais() != null) {			
			comboEstado.addAll(paramDados.consultaEstado(paisDados));
			
			return comboEstado;
		}
		estadoDados.setPkEstado(null);
		comboEstado.clear();
		return comboEstado;
	}

	public void setComboEstado(List<SelectItem> comboEstado) {
		this.comboEstado = comboEstado;
	}
	
	/* CIDADE */
	public List<SelectItem> getComboMunicipio() {
		if(estadoDados.getPkEstado() != null) {
			comboMunicipio.addAll(paramDados.consultaCidade(estadoDados));
			
			return comboMunicipio;
		}
		cidadeDados.setPkCidade(null);
		comboMunicipio.clear();
		return comboMunicipio;
	}

	public void setComboMunicipio(List<SelectItem> comboMunicipio) {
		this.comboMunicipio = comboMunicipio;
	}

	public List<SelectItem> getComboMunicipioInscricao() {
		comboMunicipioInscricao.clear();
		if( estadoInscricaoDados.getPkEstado() != null ) {
			comboMunicipioInscricao.addAll(paramDados.consultaCidade(estadoInscricaoDados));
			return comboMunicipioInscricao;
		}
		cidadeInscricaoDados.setPkCidade(null);
		return comboMunicipioInscricao;
	}

	public void setComboMunicipioInscricao(List<SelectItem> comboMunicipioInscricao) {
		this.comboMunicipioInscricao = comboMunicipioInscricao;
	}

	public TipoLogradouro getTipoLograDados() {
		return tipoLograDados;
	}

	public void setTipoLograDados(TipoLogradouro tipoLograDados) {
		this.tipoLograDados = tipoLograDados;
	}

	public Regiao getRegiaoDados() {
		return regiaoDados;
	}

	public void setRegiaoDados(Regiao regiaoDados) {
		this.regiaoDados = regiaoDados;
	}

	public List<Fornecedor> getListaConsultaFornecedores() {
		return listaConsultaFornecedores;
	}

	public void setListaConsultaFornecedores(List<Fornecedor> listaConsultaFornecedores) {
		this.listaConsultaFornecedores = listaConsultaFornecedores;
	}

	public HtmlDataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	public Boolean getPanelDadosEmpresa() {
		return panelDadosEmpresa;
	}

	public void setPanelDadosEmpresa(Boolean panelDadosEmpresa) {
		this.panelDadosEmpresa = panelDadosEmpresa;
	}
}
