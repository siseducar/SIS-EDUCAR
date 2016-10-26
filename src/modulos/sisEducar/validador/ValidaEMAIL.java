package modulos.sisEducar.validador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="valida.email")
public class ValidaEMAIL implements Validator{

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";
	
	private Pattern pattern;
	private Matcher matcher;
	
	public ValidaEMAIL(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	}
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object valorTela) throws ValidatorException {
		
		matcher = pattern.matcher(valorTela.toString());
		
		if(!matcher.matches()){

			FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("EMAIL Informado Invalido");
            throw new ValidatorException(message);
		}
	}
}
