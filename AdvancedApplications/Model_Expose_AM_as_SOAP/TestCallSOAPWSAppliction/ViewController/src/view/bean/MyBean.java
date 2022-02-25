package view.bean;

import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.xml.ws.BindingProvider;

import model.eo.common.AppModuleService;
import model.eo.common.AppModuleService_Service;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;

public class MyBean
{
  private RichInputText deptIdInputText;
  private RichOutputText soapWSResultOutputText;

  public MyBean()
  {
  }

  public void setDeptIdInputText(RichInputText deptIdInputText)
  {
    this.deptIdInputText = deptIdInputText;
  }

  public RichInputText getDeptIdInputText()
  {
    return deptIdInputText;
  }

  public void setSoapWSResultOutputText(RichOutputText soapWSResultOutputText)
  {
    this.soapWSResultOutputText = soapWSResultOutputText;
  }

  public RichOutputText getSoapWSResultOutputText()
  {
    return soapWSResultOutputText;
  }

  public void invokeWSActionListener(ActionEvent actionEvent)
  {
    Object deptId = getDeptIdInputText().getValue();
    
    
    // Call non secure SOAP WS ////////////
    /*AppModuleService_Service appModuleService_Service = new AppModuleService_Service();
    AppModuleService appModuleService = appModuleService_Service.getAppModuleServiceSoapHttpPort();

    try
    {
      
      String deptName = appModuleService.getCustomDeptName(Integer.parseInt(String.valueOf(deptId)));
      System.out.println("DeptName: " + deptName);
      getSoapWSResultOutputText().setValue(deptName);
      
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }*/
    /////////////////////////////////////////////////
    
    
    // Call secure SOAP WS ///////////////////
    
    
    AppModuleService_Service appModuleService_Service = new AppModuleService_Service();

    System.setProperty("oracle.security.jps.config", "../src/META-INF/jps-config.xml");

    // Configure security feature
    SecurityPoliciesFeature securityFeatures =
      new SecurityPoliciesFeature(new String[] { "oracle/wss_username_token_client_policy" });
    AppModuleService appModuleService = appModuleService_Service.getAppModuleServiceSoapHttpPort(securityFeatures);

    Map<String, Object> requestContext = ((BindingProvider) appModuleService).getRequestContext();
    requestContext.put("csf-key", "myKey");
    // Add your code to call the desired methods.
    try
    {
      System.out.println("DeptName: " + appModuleService.getCustomDeptName(Integer.parseInt(String.valueOf(deptId))));
       String deptName = appModuleService.getCustomDeptName(Integer.parseInt(String.valueOf(deptId)));
       System.out.println("DeptName: " + deptName);
       getSoapWSResultOutputText().setValue(deptName);
     }
    catch (Exception e)
    {
      // TODO: Add catch code
      e.printStackTrace();
    }
    
    
    
    
    
    
    
    
  }
}
