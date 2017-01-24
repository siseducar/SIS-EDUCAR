package modulos.secretaria.om;

import java.io.Serializable;

public class Terreno implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	private Integer pkTerreno;
	private Double distanciaAteSede;
	private Double areaTerrenoM2;
	private Double areaConstrucaoM2;
	private Double latitude;
	private Double longitude;
	private Integer status;
	
	public Integer getPkTerreno() {
		return pkTerreno;
	}
	public void setPkTerreno(Integer pkTerreno) {
		this.pkTerreno = pkTerreno;
	}
	public Double getDistanciaAteSede() {
		return distanciaAteSede;
	}
	public void setDistanciaAteSede(Double distanciaAteSede) {
		this.distanciaAteSede = distanciaAteSede;
	}
	public Double getAreaTerrenoM2() {
		return areaTerrenoM2;
	}
	public void setAreaTerrenoM2(Double areaTerrenoM2) {
		this.areaTerrenoM2 = areaTerrenoM2;
	}
	public Double getAreaConstrucaoM2() {
		return areaConstrucaoM2;
	}
	public void setAreaConstrucaoM2(Double areaConstrucaoM2) {
		this.areaConstrucaoM2 = areaConstrucaoM2;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
