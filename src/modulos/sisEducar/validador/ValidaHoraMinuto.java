package modulos.sisEducar.validador;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ValidaHoraMinuto implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent component, Object valorTela) throws ValidatorException {
		if (!validaHoraMinuto(String.valueOf(valorTela))) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("Hora/Minuto Informado Invalido");
            throw new ValidatorException(message);
       }
	}
	
	/**
     * Valida se o horário informado existe
     *
     */
     private static boolean validaHoraMinuto(String horaMinuto) 
     {
	      try 
	      {
	    	  if (horaMinuto == null || horaMinuto.length() != 4 || !isHoraMinutoPadrao(horaMinuto))
	    	  {     
	    		  return false;
	    	  }
	    	  else
	    	  {
	    		  return true;
	    	  }
	      } catch (NumberFormatException e) { 
	    	  return false;
	      }
     }
	
     /**
    *
    * @param horaMinuto String valor a ser testado
    * @return boolean indicando se o usuário entrou com uma hora minuto valida
    */
    private static boolean isHoraMinutoPadrao(String horaMinuto) {
    	 String hora;
    	 String minuto;
    	 if(horaMinuto!=null && horaMinuto.length()==4)
    	 {
    		 hora = horaMinuto.substring(0,2);
    		 minuto = horaMinuto.substring(2, 4);
    		 if((Long.parseLong(hora)<0 || Long.parseLong(hora)>24) || (Long.parseLong(minuto) <0 || Long.parseLong(minuto) > 60)) 
    		 {
    			 return false;
    		 }
    		 else
    		 {
    			 return true;
    		 }
    	 }
    	 
    	 return false;
    }
}
