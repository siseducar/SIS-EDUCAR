package modulos.RH.utils;

import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="selectOneView")
@SessionScoped
public class SelectOneView 
{
    private String option;   
    private Theme theme; 
    private List<Theme> themesNomesUsuarios;
     
    private ThemeService themeService;
    
    public SelectOneView() throws SQLException
    {
    	themeService = new ThemeService();
    	themeService.populaListaNomesUsuario();
    	themesNomesUsuarios = themeService.getThemesNomesUsuarios();
    }
 
    /* Getters and setters*/
    public String getOption() {
        return option;
    }
 
    public void setOption(String option) {
        this.option = option;
    }
 
    public Theme getTheme() {
        return theme;
    }
 
    public void setTheme(Theme theme) {
        this.theme = theme;
    }
 
    public void setService(ThemeService service) {
        this.themeService = service;
    }

	public List<Theme> getThemesNomesUsuarios() {
		return themesNomesUsuarios;
	}

	public void setThemesNomesUsuarios(List<Theme> themesNomesUsuarios) {
		this.themesNomesUsuarios = themesNomesUsuarios;
	}
}