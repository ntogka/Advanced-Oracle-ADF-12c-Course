
package model.eo.common.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="departmentId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "departmentId" })
@XmlRootElement(name = "getDepartmentsView1")
public class GetDepartmentsView1
{

  protected int departmentId;

  /**
   * Gets the value of the departmentId property.
   *
   */
  public int getDepartmentId()
  {
    return departmentId;
  }

  /**
   * Sets the value of the departmentId property.
   *
   */
  public void setDepartmentId(int value)
  {
    this.departmentId = value;
  }

}
