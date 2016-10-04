package modulos.educacao.om;

import java.util.List;

import modulos.secretaria.om.Turno;
import modulos.secretaria.om.UnidadeEscolar;

public class Horario 
{
	private Integer pkHorario;
	private Double horaInicio;
	private Double minutoInicio;
	private String inicioAux;
	private Double horaTermino;
	private Double minutoTermino;
	private String terminoAux;
	private Double horaIntervalo;
	private Double minutoIntervalo;
	private String intervaloAux;
	private Double horaHoraAula;
	private Double minutoHoraAula;
	private String horaAulaAux;
	private Integer status;
	private Turno turno;
	private List<HorarioAula> horariosAula;
	
	private UnidadeEscolar unidadeEscolar;

	public Integer getPkHorario() {
		return pkHorario;
	}

	public void setPkHorario(Integer pkHorario) {
		this.pkHorario = pkHorario;
	}

	public Double getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Double horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Double getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(Double minutoInicio) {
		this.minutoInicio = minutoInicio;
	}

	public String getInicioAux() {
		return inicioAux;
	}

	public void setInicioAux(String inicioAux) {
		this.inicioAux = inicioAux;
	}

	public Double getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(Double horaTermino) {
		this.horaTermino = horaTermino;
	}

	public Double getMinutoTermino() {
		return minutoTermino;
	}

	public void setMinutoTermino(Double minutoTermino) {
		this.minutoTermino = minutoTermino;
	}

	public String getTerminoAux() {
		return terminoAux;
	}

	public void setTerminoAux(String terminoAux) {
		this.terminoAux = terminoAux;
	}

	public Double getHoraIntervalo() {
		return horaIntervalo;
	}

	public void setHoraIntervalo(Double horaIntervalo) {
		this.horaIntervalo = horaIntervalo;
	}

	public Double getMinutoIntervalo() {
		return minutoIntervalo;
	}

	public void setMinutoIntervalo(Double minutoIntervalo) {
		this.minutoIntervalo = minutoIntervalo;
	}

	public String getIntervaloAux() {
		return intervaloAux;
	}

	public void setIntervaloAux(String intervaloAux) {
		this.intervaloAux = intervaloAux;
	}

	public Double getHoraHoraAula() {
		return horaHoraAula;
	}

	public void setHoraHoraAula(Double horaHoraAula) {
		this.horaHoraAula = horaHoraAula;
	}

	public Double getMinutoHoraAula() {
		return minutoHoraAula;
	}

	public void setMinutoHoraAula(Double minutoHoraAula) {
		this.minutoHoraAula = minutoHoraAula;
	}

	public String getHoraAulaAux() {
		return horaAulaAux;
	}

	public void setHoraAulaAux(String horaAulaAux) {
		this.horaAulaAux = horaAulaAux;
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

	public List<HorarioAula> getHorariosAula() {
		return horariosAula;
	}

	public void setHorariosAula(List<HorarioAula> horariosAula) {
		this.horariosAula = horariosAula;
	}

	public UnidadeEscolar getUnidadeEscolar() {
		return unidadeEscolar;
	}

	public void setUnidadeEscolar(UnidadeEscolar unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}
	
}
