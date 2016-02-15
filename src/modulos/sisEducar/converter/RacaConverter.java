package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import modulos.RH.om.Raca;

@FacesConverter(value="racaConverter", forClass=Raca.class)
public class RacaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent componente, String value) {
		System.out.println("Estou recebendo a String: " + value);
		
		if(value != null && !value.equals("") ) {
			componente.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object != null && object instanceof Raca) {
			return ((Raca)object).getPkRaca().toString();
		}
		return null;
	}

}
