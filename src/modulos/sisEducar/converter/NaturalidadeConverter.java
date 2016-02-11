package modulos.sisEducar.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import modulos.RH.dao.NacionalidadeDAO;
import modulos.RH.om.Nacionalidade;

public class NaturalidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")) {
			try {
				return new NacionalidadeDAO().consultaNacionalidade();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object != null && object instanceof Nacionalidade) {
			return ((Nacionalidade) object).getPkNacionalidade().toString();
		}
		return null;
	}

}
