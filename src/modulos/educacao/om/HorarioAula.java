package modulos.educacao.om;

public class HorarioAula 
{
	private Integer pkHorarioAula;
	private Double inicio;
	private Double fim;
	private Integer status;
	private Horario horario;
	
	public Integer getPkHorarioAula() {
		return pkHorarioAula;
	}
	public void setPkHorarioAula(Integer pkHorarioAula) {
		this.pkHorarioAula = pkHorarioAula;
	}
	public Double getInicio() {
		return inicio;
	}
	public void setInicio(Double inicio) {
		this.inicio = inicio;
	}
	public Double getFim() {
		return fim;
	}
	public void setFim(Double fim) {
		this.fim = fim;
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
}
