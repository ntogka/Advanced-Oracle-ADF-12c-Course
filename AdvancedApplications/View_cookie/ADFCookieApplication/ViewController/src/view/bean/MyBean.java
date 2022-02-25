package view.bean;

import javax.faces.context.FacesContext;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;

public class MyBean
{
  private RichSelectBooleanCheckbox rememberMeCheckBox;

  public MyBean()
  {
    String userCookie = getCookieValue("usernameCookie");
    String passwordCookie = getCookieValue("passwordCookie");
    System.out.println("userCookie: " + userCookie + " - passwordCookie: " + passwordCookie);
    if(userCookie != null && !userCookie.trim().equals("") && !userCookie.equals("null"))
    {
      setUserName(userCookie);
    }
    
    if(passwordCookie != null && !passwordCookie.trim().equals("") && !passwordCookie.equals("null"))
    {
      setPassword(passwordCookie);
    }
  }
  
  private String userName;
  private String password;
  
  public String loginAction()
  {
    if(getRememberMeCheckBox().getValue() != null && getRememberMeCheckBox().getValue().toString().equals("true"))
    {
      System.out.println("Cookie Added: " + userName + " - password: " + password);
      addCookie("usernameCookie", userName);
      addCookie("passwordCookie", password);
    }
    else
    {
      removeCookie("usernameCookie");
      removeCookie("passwordCookie");
    }
    return "main";
  }
  
  
  public void addCookie(String cookieName, String cookieValue)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();

    Cookie cookie = new Cookie(cookieName, cookieValue);
    cookie.setMaxAge(60*60*24*365*5);
    response.addCookie(cookie);
  }

  public void removeCookie(String cookieName)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
    Cookie[] cookies = request.getCookies();

    if (cookies != null)
    {
      for (int i = 0; i < cookies.length; i++)
      {
        String name = cookies[i].getName();

        if ((name == null) || (!name.equals(cookieName)))
          continue;
        cookies[i].setMaxAge(0);
        response.addCookie(cookies[i]);
        break;
      }
    }
  }

  public String getCookieValue(String cookieName)
  {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
    Cookie[] cookies = request.getCookies();

    if (cookies != null)
    {
      for (int i = 0; i < cookies.length; i++)
      {
        String name = cookies[i].getName();
        String value = cookies[i].getValue();
        int age = cookies[i].getMaxAge();

        if ((name != null) && (name.equals(cookieName)) && (age != 0))
        {
          return value;
        }
      }
    }
    return null;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getPassword()
  {
    return password;
  }

  public void setRememberMeCheckBox(RichSelectBooleanCheckbox rememberMeCheckBox)
  {
    this.rememberMeCheckBox = rememberMeCheckBox;
  }

  public RichSelectBooleanCheckbox getRememberMeCheckBox()
  {
    return rememberMeCheckBox;
  }
}
