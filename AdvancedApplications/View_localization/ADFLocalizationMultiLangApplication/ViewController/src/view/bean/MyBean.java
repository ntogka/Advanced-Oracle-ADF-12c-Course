package view.bean;

import java.util.Locale;
import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.share.ADFContext;

public class MyBean
{
  public MyBean()
  {
  }

  public String englishLanguageAction()
  {
    switchLanguage("en");
    return null;
  }

  public String arabicLanguageAction()
  {
    switchLanguage("ar");
    return null;
  }
  
  public static void storeOnSession(String key, Object object)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    sessionState.put(key, object);
  }

  public static Object getFromSession(String key)
  {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    return sessionState.get(key);
  }

  public static String switchLanguage(String lang)
  {
    storeOnSession("lang", lang);
    FacesContext context = FacesContext.getCurrentInstance();
    Locale locale = new Locale(lang);
    context.getViewRoot().setLocale(locale);
    ADFContext.getCurrent().setLocale(locale);
    return null;
  }
  
  public String getCurrentLocale()
  {
    return (String) getFromSession("lang");
  }
}
