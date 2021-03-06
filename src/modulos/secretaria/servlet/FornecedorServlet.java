package modulos.secretaria.servlet;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import modulos.secretaria.dao.AmbienteDAO;
import modulos.secretaria.dao.FornecedorDAO;
import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.dao.UnidadeEscolarDAO;
import modulos.secretaria.om.Cidade;
import modulos.secretaria.om.Contato;
import modulos.secretaria.om.Endereco;
import modulos.secretaria.om.Estado;
import modulos.secretaria.om.Fornecedor;
import modulos.secretaria.om.Pais;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.Regiao;
import modulos.secretaria.om.TipoLogradouro;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.services.ContatoService;
import modulos.secretaria.services.EnderecoService;
import modulos.secretaria.services.FornecedorService;
import modulos.secretaria.utils.ConstantesSecretaria;
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
	
	private Boolean btCadastrar;
	private Boolean btExcluir;
	private Boolean btConsultar;
	
	Boolean temPermissaoCadastrar = false;
	Boolean temPermissaoExcluir = false;
	Boolean temPermissaoConsultar = false;
		
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
		
		btExcluir = false;
		btCadastrar = false;
		btConsultar = false;
		
		usuarioLogadao = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		validarPermissoes();
		if(temPermissaoCadastrar) { btCadastrar = true; }
		if(temPermissaoConsultar) { btConsultar = true; }
		if(temPermissaoExcluir) { btExcluir = true; }
		pessoaDados.setFkMunicipioCliente(usuarioLogadao.getFkMunicipioCliente());
	}
	
	public void validarPermissoes()
	{
		//validar permissão
		for (Permissao permissao : usuarioLogadao.getPermissoes()) 
		{
			if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_EXCLUIR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR))
			{
				temPermissaoExcluir = true;
			}
			else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CADASTRAR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR))
			{
				temPermissaoCadastrar = true;
			}
			else if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CONSULTAR) 
					&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_CADASTROS_FORNECEDOR))
			{
				temPermissaoConsultar = true;
			}
		}
	}
	
	public void deletarCadastro() throws SQLException
	{
		Boolean resultado = false;
		if(fornecedorDados!=null && fornecedorDados.getPkFornecedor()!=null)
		{
			resultado = new FornecedorDAO().remover(fornecedorDados);
			if(resultado)
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "O fornecedor foi removido com sucesso", null));
				resetarFormulario();
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O fornecedor não foi removida", null));
			}
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Salve o fornecedor antes de tentar remover", null));
		}
	}
	public void validaCadastro() {
		if( validaDados() ) {
			salvarCadastroFornecedor();
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
					resetarFormulario();
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
		
		return true;
	}
	
	
	public void consultaNomeContato() {
		try {
			Fornecedor fornecedorAux = null; 
			Estado estadoPadrao = new Estado();
			estadoPadrao.setPkEstado(25);
			if( pessoaDados.getCpf() != null && pessoaDados.getCpf() > 0) {				
				
				pessoaDados = new PessoaDAO().obtemUnicoPessoaSimples(pessoaDados.getCpf().toString());
				if( pessoaDados.getNome() != null ) {
					panelDadosEmpresa = true;
					
					fornecedorAux = new FornecedorDAO().obtemFornecedor(pessoaDados.getPkPessoa());
					if(fornecedorAux!=null)
					{
						fornecedorDados = fornecedorAux;
						enderecoDados = fornecedorDados.getEndereco();
						estadoInscricaoDados = fornecedorDados.getEstadoInscricao();
						cidadeDados = fornecedorDados.getEndereco().getCidade();
						tipoLograDados = enderecoDados.getTipologradouro();
						regiaoDados = enderecoDados.getRegiao();
						contatoDados = enderecoDados.getContato();
						
						if(fornecedorDados.getCidadeInscricao()!=null)
						{
							if( comboMunicipioInscricao == null || comboMunicipioInscricao.isEmpty()) { 
								comboMunicipioInscricao = paramDados.consultaCidade(estadoPadrao); 
							}
							for(SelectItem selectItem : comboMunicipioInscricao) {
								if(selectItem.getValue().equals(fornecedorDados.getCidadeInscricao().getPkCidade())) {
									cidadeInscricaoDados = new Cidade();
									cidadeInscricaoDados.setPkCidade(fornecedorDados.getCidadeInscricao().getPkCidade());
									break;
								}
							}
						}
						
						if(enderecoDados!=null)
						{
							if(cidadeDados != null) {
								if(cidadeDados.getEstado().getPais().getPkPais() != null) {
									for(SelectItem selectItem : paramDados.getComboPais()) {
										if(selectItem.getValue().equals(cidadeDados.getEstado().getPais().getPkPais())) {
											paisDados = new Pais();
											paisDados.setPkPais(cidadeDados.getEstado().getPais().getPkPais());
											break;
										}
									}
								}
								if(paisDados != null && paisDados.getPkPais() != null) {
									if( comboEstado == null || comboEstado.isEmpty()) {
										comboEstado = paramDados.consultaEstado(paisDados);
									}
									for(SelectItem selectItem : comboEstado) {
										if(selectItem.getValue().equals(cidadeDados.getEstado().getPkEstado())) {
											estadoDados = new Estado();
											estadoDados.setPkEstado(cidadeDados.getEstado().getPkEstado());
											break;
										}
									}
								}
								if(estadoDados != null && estadoDados.getPkEstado() != null){
									if( comboMunicipio == null || comboMunicipio.isEmpty()) { 
										comboMunicipio = paramDados.consultaCidade(estadoDados); 
									}
									for(SelectItem selectItem : comboMunicipio) {
										if(selectItem.getValue().equals(enderecoDados.getCidade().getPkCidade())) {
											cidadeDados = new Cidade();
											cidadeDados.setPkCidade(enderecoDados.getCidade().getPkCidade());
											break;
										}
									}
								}
							}
						}
					}
				} else {
					resetarFormulario();
					pessoaDados.setCpf(null);
					pessoaDados.setNome(null);
					panelDadosEmpresa = false;
					Logs.addInfo("CPF não encontrado!", null);
				}
			} else {
				resetarFormulario();
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
	
	public void resetarFormulario() {
		
		fornecedorDados = new Fornecedor();
		enderecoDados = new Endereco();
		estadoInscricaoDados = new Estado();
		cidadeDados = new Cidade();
		tipoLograDados = new TipoLogradouro();
		regiaoDados = new Regiao();
		contatoDados = new Contato();
		
		paisDados = new Pais();
		estadoDados = new Estado();
		cidadeDados = new Cidade();
		
		panelDadosEmpresa = false;
		
		pessoaDados = new Pessoa();
		
		btExcluir = false;
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
		Estado estado = new Estado();
		estado.setPkEstado(25);
		comboMunicipioInscricao.clear();
		
		comboMunicipioInscricao.addAll(paramDados.consultaCidade(estado));
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

	public Boolean getBtExcluir() {
		return btExcluir;
	}

	public void setBtExcluir(Boolean btExcluir) {
		this.btExcluir = btExcluir;
	}

	public Boolean getBtCadastrar() {
		return btCadastrar;
	}

	public void setBtCadastrar(Boolean btCadastrar) {
		this.btCadastrar = btCadastrar;
	}

	public Boolean getBtConsultar() {
		return btConsultar;
	}

	public void setBtConsultar(Boolean btConsultar) {
		this.btConsultar = btConsultar;
	}
}
