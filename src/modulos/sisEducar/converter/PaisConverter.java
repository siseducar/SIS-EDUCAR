package modulos.sisEducar.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modulos.RH.dao.PaisDAO;
import modulos.RH.om.Pais;

@FacesConverter(value="paisConverter", forClass=Pais.class)
public class PaisConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent componente, String value) {
		if(value != null && !value.equals("") ) {
			try {
				return new PaisDAO().consultaPais();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object != null && object instanceof Pais) {
			return ((Pais)object).getPkPais().toString();
		}
		return null;
	}

}
