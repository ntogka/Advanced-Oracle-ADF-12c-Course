package view.bean;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import view.common.CommonUtil;

public class MyBean
{
  public MyBean()
  {
  }
  
  private java.util.List<Department> tableDepartments;


  public void setTableDepartments(List<Department> tableDepartments)
  {
    this.tableDepartments = tableDepartments;
  }

  public List<Department> getTableDepartments()
  {
    if(tableDepartments == null)
    {
      tableDepartments = getListOfDeptsFromREST();
    }
    return tableDepartments;
  }
  
  private List<Department> getListOfDeptsFromREST()
  {
    try
    {
      String restOutput = CommonUtil.invokeGetREST("http://127.0.0.1:7101/ExposeAMToRESTApplication-RESTWebService-context-root/rest/1/depts?limit=10","application/vnd.oracle.adf.action+json");
      
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      Items depts =  mapper.readValue(restOutput, Items.class);
      
      return depts.getDepartments();
      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return new ArrayList<Department>();
  }
}
