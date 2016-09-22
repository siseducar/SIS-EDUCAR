package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverteHoraMinuto implements Converter {

	  public Object getAsObject(FacesContext context, UIComponent component, String value) {
		  String horaMinuto = value;
			int digitos = value.replace(":", "").length();
			if(digitos == 4) {
				horaMinuto = value.replace(":", "");
			}
			
			return horaMinuto;
	  }
	  
	  @Override
	  public String getAsString(FacesContext context, UIComponent component,
	            Object value) {
			String horaMinuto = value.toString();
			if( horaMinuto != null && horaMinuto.length() == 4) {
				horaMinuto = horaMinuto.substring(0,2) + ":" + horaMinuto.substring(2,4);
			} else {
				horaMinuto = null;
			}
			
			return horaMinuto;
	   }    
}