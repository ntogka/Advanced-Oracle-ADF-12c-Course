package model.eo.common;

import java.util.Map;

import javax.xml.ws.BindingProvider;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;
// This source file is generated by Oracle tools.
// Contents may be subject to change.
// For reporting problems, use the following:
// Generated by Oracle JDeveloper 12c 12.2.1.3.0.0914
public class AppModuleServiceSoapHttpPortClient
{
  public static void main(String[] args)
  {
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
      System.out.println("DeptName: " + appModuleService.getCustomDeptName(10));
     }
    catch (Exception e)
    {
      // TODO: Add catch code
      e.printStackTrace();
    }

  }
}
