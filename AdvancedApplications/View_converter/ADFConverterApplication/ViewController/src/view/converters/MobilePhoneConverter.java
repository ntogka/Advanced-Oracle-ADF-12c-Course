package view.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class MobilePhoneConverter implements Converter
{

  @Override
  public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String string) // when user set the value 08xxxxxxxx
  {
    
    if(string == null || string.trim().equals(""))
    {
      return null;
    }
    
    if (string.length() != 10)
    {
      FacesMessage message = new FacesMessage("You have to enter mobile number in this format [08xxxxxxxx].");
      message.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ConverterException(message);
    }
    
    String value = string.substring(1, 10);
    
    return "353" + value;
  }

  @Override
  public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object object) // return the value to user 3538xxxxxxxx
  {
    
    if(object == null)
    {
      return null;
    }
    
    String value = String.valueOf(object);
    String number = value.substring(3, 12);
    
    return "0" + number;
  }
}
