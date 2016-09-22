package modulos.educacao.om;

import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;

public class Horario 
{
	private Integer pkHorario;
	private Double inicio;
	private Double termino;
	private Double intervalo;
	private Double horaAula;
	private Integer status;
	private Turno turno;
	
	private UnidadeEscolar unidadeEscolar;
	public Integer getPkHorario() {
		return pkHorario;
	}
	public void setPkHorario(Integer pkHorario) {
		this.pkHorario = pkHorario;
	}
	public Double getInicio() {
		return inicio;
	}
	public void setInicio(Double inicio) {
		this.inicio = inicio;
	}
	public Double getTermino() {
		return termino;
	}
	public void setTermino(Double termino) {
		this.termino = termino;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}
	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	public Double getIntervalo() {
		return intervalo;
	}
	public void setIntervalo(Double intervalo) {
		this.intervalo = intervalo;
	}
	public Double getHoraAula() {
		return horaAula;
	}
	public void setHoraAula(Double horaAula) {
		this.horaAula = horaAula;
	}
}
