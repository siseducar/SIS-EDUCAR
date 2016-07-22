package modulos.sisEducar.om;

import java.sql.Date;

public class Anexo 
{
	private Integer pkAnexo;
	private String nome;
	private String nomeFisico;
	private String extensao;
	private Date dataLancamento;
	private Integer status;
	private byte[] bytes;
	private TipoAnexo tipoAnexo;
	
	public Integer getPkAnexo() {
		return pkAnexo;
	}
	public void setPkAnexo(Integer pkAnexo) {
		this.pkAnexo = pkAnexo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNomeFisico() {
		return nomeFisico;
	}
	public void setNomeFisico(String nomeFisico) {
		this.nomeFisico = nomeFisico;
	}
	public String getExtensao() {
		return extensao;
	}
	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}
	public Date getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public TipoAnexo getTipoAnexo() {
		return tipoAnexo;
	}
	public void setTipoAnexo(TipoAnexo tipoAnexo) {
		this.tipoAnexo = tipoAnexo;
	}
}
