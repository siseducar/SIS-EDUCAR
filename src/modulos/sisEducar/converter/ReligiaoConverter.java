package modulos.sisEducar.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import modulos.RH.dao.ReligiaoDAO;
import modulos.RH.om.Religiao;

public class ReligiaoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")) {
			try {
				return new ReligiaoDAO().consultaReligiao();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object != null && object instanceof Religiao) {
			return ((Religiao) object).getPkReligiao().toString();
		}
		return null;
	}

}
