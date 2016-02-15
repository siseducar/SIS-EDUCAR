package modulos.sisEducar.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modulos.RH.dao.SituacaoEconomicaDAO;
import modulos.RH.om.SituacaoEconomica;

@FacesConverter(value="situEconomicaConverter", forClass=SituacaoEconomica.class)
public class SituEconomicaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && !value.equals("")) {
			try {
				return new SituacaoEconomicaDAO().consultaSituEconomica();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object != null && object instanceof SituacaoEconomica) {
			return ((SituacaoEconomica)object).getPkSituacaoEconomica().toString();
		}
		return null;
	}

}
