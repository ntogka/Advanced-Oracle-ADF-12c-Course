package view.bean;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MyBean
{
  public MyBean()
  {
  }

  public String button1Action()
  {
    System.out.println("I am in Button1 Action");
    return null;
  }

  public void pageLoad(ClientEvent clientEvent)
  {
    System.out.println("Page is loaded");
  }

  public void deptTableDBClickListener(ClientEvent clientEvent)
  {
    String deptName = (String) clientEvent.getParameters().get("deptName");
    System.out.println("deptName: " + deptName);
    showPopup("p2");
  }
  
  public void showPopup(String popupName)
  {
    StringBuilder strb = new StringBuilder("AdfPage.PAGE.findComponentByAbsoluteId(\"" + popupName + "\").show();");
    FacesContext fctx = FacesContext.getCurrentInstance();
    ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
    erks.addScript(fctx, strb.toString());
  }
}
