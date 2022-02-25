package view.bean;

import org.codehaus.jackson.annotate.JsonProperty;

public class Department
{
  @JsonProperty("DepartmentId")
  private String departmentId;
  
  @JsonProperty("DepartmentName")
  private String departmentName;
  
  @JsonProperty("LocationId")
  private String locationId;


  public void setDepartmentId(String departmentId)
  {
    this.departmentId = departmentId;
  }

  public String getDepartmentId()
  {
    return departmentId;
  }

  public void setDepartmentName(String departmentName)
  {
    this.departmentName = departmentName;
  }

  public String getDepartmentName()
  {
    return departmentName;
  }

  public void setLocationId(String locationId)
  {
    this.locationId = locationId;
  }

  public String getLocationId()
  {
    return locationId;
  }


}
