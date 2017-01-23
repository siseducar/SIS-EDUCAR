package modulos.secretaria.servlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modulos.secretaria.dao.PessoaDAO;
import modulos.secretaria.om.Permissao;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.PessoaRelatorio;
import modulos.secretaria.om.Usuario;
import modulos.secretaria.utils.ConstantesSecretaria;
import modulos.sisEducar.relatorios.Relatorio;
import modulos.sisEducar.utils.ConstantesSisEducar;
import modulos.sisEducar.utils.Logs;

@ManagedBean(name="pessoaRelatorioServlet")
@ViewScoped
public class PessoaRelatorioServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Boolean btConsultarRelatorioEnabled;
	private Usuario usuarioLogado = null;
	private Boolean temPermissaoConsultarRelatorio;
	private String nomePesquisar;
	private String cpfPesquisar;
	
	public PessoaRelatorioServlet()
	{
		nomePesquisar = "";
		cpfPesquisar = "";
		btConsultarRelatorioEnabled = false;
		
		usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		
		validarPermissoesTela();
		if(temPermissaoConsultarRelatorio) { btConsultarRelatorioEnabled = true; }
	}
	
	public void validarPermissoesTela()
	{
		try 
		{
			if(usuarioLogado!=null)
			{
				for (Permissao permissao : usuarioLogado.getPermissoes()) 
				{
					if(permissao.getTipo().equals(ConstantesSecretaria.PERMISSAO_CONSULTAR) 
							&& permissao.getTelaResponsavel().equals(ConstantesSecretaria.PERMISSAO_TIPO_SECRETARIA_RELATORIOS_PESSOAS))
					{
						temPermissaoConsultarRelatorio = true;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			Logs.addError("validarPermissoesTela", "validarPermissoesTela");
		}
	}
	
	/**
	 * Usado para limpar o formuario da tela de relatorio pessoa
	 * @author João Paulo
	 */
	public void resetarRelatorioPessoa()
	{
		try 
		{
			
		} 
		catch (Exception e) 
		{
			Logs.addError("resetarRelatorioPessoa", "Erro ao limpar");
		}
	}
	
	/**
	 * Usado para imprimir o relatório de pessoas
	 * @author João Paulo
	 */
	public void imprimirRelatorio()
	{
		try 
		{
			List<PessoaRelatorio> lista = new ArrayList<PessoaRelatorio>();
			List<Pessoa> pessoas = new PessoaDAO().obtemTodosFiltros(cpfPesquisar, nomePesquisar);
			PessoaRelatorio pessoaRelatorio = null;
			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
			
			for (Pessoa pessoa : pessoas) 
			{
				pessoaRelatorio = new PessoaRelatorio();
				pessoaRelatorio.setNome(pessoa.getNome());
				
				if(pessoa.getCpf()!=null) 				{ pessoaRelatorio.setCpf(pessoa.getCpf().toString()); }
				if(pessoa.getSexo().equals("M")) 		{ pessoaRelatorio.setSexo("Masculino"); }
				else 							 		{ pessoaRelatorio.setSexo("Feminino"); }
				if(pessoa.getDataNascimento()!=null)	{ pessoaRelatorio.setDataNascimento(dataFormatada.format(pessoa.getDataNascimento())); }
				
				lista.add(pessoaRelatorio);
			}
			
			System.out.println("Gerando relatório de teste...");
			Relatorio.gerarArquivoPDF(lista, "C:\\relatoriosTemp\\RelatoriosClientes.jrxml", 
					ConstantesSisEducar.PATH_PROJETO_JOAO + ConstantesSisEducar.PATH_DESTINO_RELATORIOS_LOCAL, "RelatorioClientes");
			System.out.println("Relatório gerado.");
		} 
		catch (Exception e) 
		{
			Logs.addError("imprimirRelatorio", "Erro ao imprimir");
		}
	}

	public Boolean getBtConsultarRelatorioEnabled() {
		return btConsultarRelatorioEnabled;
	}

	public void setBtConsultarRelatorioEnabled(Boolean btConsultarRelatorioEnabled) {
		this.btConsultarRelatorioEnabled = btConsultarRelatorioEnabled;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getNomePesquisar() {
		return nomePesquisar;
	}

	public void setNomePesquisar(String nomePesquisar) {
		this.nomePesquisar = nomePesquisar;
	}

	public String getCpfPesquisar() {
		return cpfPesquisar;
	}

	public void setCpfPesquisar(String cpfPesquisar) {
		this.cpfPesquisar = cpfPesquisar;
	}
}
