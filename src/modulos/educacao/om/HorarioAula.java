package modulos.educacao.om;

public class HorarioAula 
{
	private Integer pkHorarioAula;
	private Double horaInicio;
	private String inicioAux;
	private Double minutoInicio;
	private Double horaTermino;
	private String terminoAux;
	private Double minutoTermino;
	private Integer status;
	private Horario horario;
	public Integer getPkHorarioAula() {
		return pkHorarioAula;
	}
	public void setPkHorarioAula(Integer pkHorarioAula) {
		this.pkHorarioAula = pkHorarioAula;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Horario getHorario() {
		return horario;
	}
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	public String getInicioAux() {
		return inicioAux;
	}
	public void setInicioAux(String inicioAux) {
		this.inicioAux = inicioAux;
	}
	public String getTerminoAux() {
		return terminoAux;
	}
	public void setTerminoAux(String terminoAux) {
		this.terminoAux = terminoAux;
	}
	
}
