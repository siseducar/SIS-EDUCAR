package modulos.sisEducar.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverteUSUARIO implements Converter {

	  public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    System.out.println(value);
	    
	    return null;
	  }
	  
	  @Override
	  public String getAsString(FacesContext context, UIComponent component,
	            Object value) {
	    // convert from Object to String
		  
		  return null;
	   }    
}