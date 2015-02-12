package modulos.RH.utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import modulos.RH.dao.PessoaDAO;
import modulos.RH.om.Pessoa;
 
@ManagedBean(name="themeService", eager = true)
@ApplicationScoped
public class ThemeService {
     
    private List<Theme> themes;
    private List<Theme> themesNomesUsuarios;
    List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
     
    @PostConstruct
    public void init() throws SQLException 
    {
    	populaListaNomesUsuario();
    }
    
    public void populaListaNomesUsuario() throws SQLException
    {
    	themesNomesUsuarios = new ArrayList<Theme>();
    	listaPessoas = new PessoaDAO().obtemTodos();
    	
    	if(listaPessoas!=null && listaPessoas.size()>0)
    	{
    		for (int i = 0; i < listaPessoas.size(); i++) 
    		{
    			if(listaPessoas.get(i).getPessoaFisica()!=null)
    			{
    				themesNomesUsuarios.add(new Theme(i, listaPessoas.get(i).getPessoaFisica().getNome(), listaPessoas.get(i).getPessoaFisica().getNome()));
    			}
    			else
    			{
    				themesNomesUsuarios.add(new Theme(i, listaPessoas.get(i).getPessoaJuridica().getNomeFantasia(), listaPessoas.get(i).getPessoaJuridica().getNomeFantasia()));
    			}
    		}
    	}
    }
     
    /* Getters and setters*/
    public List<Theme> getThemes() {
        return themes;
    }

	public List<Theme> getThemesNomesUsuarios() {
		return themesNomesUsuarios;
	}

	public void setThemesNomesUsuarios(List<Theme> themesNomesUsuarios) {
		this.themesNomesUsuarios = themesNomesUsuarios;
	} 
}