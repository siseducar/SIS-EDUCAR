package modulos.educacao.servlet;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modulos.educacao.dao.AlunoDAO;
import modulos.educacao.om.Aluno;
import modulos.secretaria.om.Curso;
import modulos.secretaria.om.Etapa;
import modulos.secretaria.om.Pessoa;
import modulos.secretaria.om.RedeEnsino;
import modulos.secretaria.om.TipoDeficiencia;
import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;

@ManagedBean(name="matriAlunoServlet")
@ViewScoped
public class matriculaAlunoServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/* Atributos */
	private Pessoa pessoaDados;
	private Aluno alunoDados;
	private RedeEnsino redeEnsinoDados;
	private UnidadeEscolar unidadeEscolarDados;
	private Etapa etapaEscolarDados;
	private Curso cursoDados;
	private Turno turnoDados;
	private TipoDeficiencia tipoDeficienciaDados;
	
	private AlunoDAO alunoDAO;
	
}
