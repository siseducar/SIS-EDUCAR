package modulos.sisEducar.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modulos.RH.dao.GrauInstrucaoDAO;
import modulos.RH.om.GrauInstrucao;

@FacesConverter(value="grauInstruConverter", forClass=GrauInstrucao.class)
public class GrauIntrucaoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String valor) {
		if(valor != null && !valor.equals("")) {
			try {
				return new GrauInstrucaoDAO().consultaGrauInstru();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object != null && object instanceof GrauInstrucao) {
			return ((GrauInstrucao)object).getPkGrauInstrucao().toString();
		}
		return null;
	}

}
