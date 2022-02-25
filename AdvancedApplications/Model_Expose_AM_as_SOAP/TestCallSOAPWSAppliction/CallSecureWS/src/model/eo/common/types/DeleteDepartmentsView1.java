
package model.eo.common.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import model.vo.common.DepartmentsViewSDO;


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
 *         &lt;element name="departmentsView1" type="{/model/vo/common/}DepartmentsViewSDO"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "departmentsView1" })
@XmlRootElement(name = "deleteDepartmentsView1")
public class DeleteDepartmentsView1
{

  @XmlElement(required = true)
  protected DepartmentsViewSDO departmentsView1;

  /**
   * Gets the value of the departmentsView1 property.
   *
   * @return
   *     possible object is
   *     {@link DepartmentsViewSDO }
   *
   */
  public DepartmentsViewSDO getDepartmentsView1()
  {
    return departmentsView1;
  }

  /**
   * Sets the value of the departmentsView1 property.
   *
   * @param value
   *     allowed object is
   *     {@link DepartmentsViewSDO }
   *
   */
  public void setDepartmentsView1(DepartmentsViewSDO value)
  {
    this.departmentsView1 = value;
  }

}
