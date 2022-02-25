package view.bean;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Items
{
  @JsonProperty("items")
  private List<Department> departments;


  public void setDepartments(List<Department> departments)
  {
    this.departments = departments;
  }

  public List<Department> getDepartments()
  {
    return departments;
  }
}
