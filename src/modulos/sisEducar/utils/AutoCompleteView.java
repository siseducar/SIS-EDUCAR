package modulos.sisEducar.utils;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
 
@ManagedBean
public class AutoCompleteView {
     
    private String txt1;
    private Theme theme1;
    private List<Theme> selectedThemes;
     
    @ManagedProperty("#{themeService}")
    private ThemeService service;
     
    public List<String> completeText(String query) {
        List<String> results = new ArrayList<String>();
        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }
         
        return results;
    }
     
    public List<Theme> completeTheme(String query) {
        List<Theme> allThemes = service.getThemes();
        List<Theme> filteredThemes = new ArrayList<Theme>();
         
        for (int i = 0; i < allThemes.size(); i++) {
            Theme skin = allThemes.get(i);
            if(skin.getName().toLowerCase().startsWith(query)) {
                filteredThemes.add(skin);
            }
        }
         
        return filteredThemes;
    }
     
    public void onItemSelect(SelectEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item Selected", event.getObject().toString()));
    }
 
    public String getTxt1() {
        return txt1;
    }
 
    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }
 
    public List<Theme> getSelectedThemes() {
        return selectedThemes;
    }
 
    public void setSelectedThemes(List<Theme> selectedThemes) {
        this.selectedThemes = selectedThemes;
    }
     
    public void setService(ThemeService service) {
        this.service = service;
    }
 
    public char getThemeGroup(Theme theme) {
        return theme.getDisplayName().charAt(0);
    }

	public Theme getTheme1() {
		return theme1;
	}

	public void setTheme1(Theme theme1) {
		this.theme1 = theme1;
	}
}