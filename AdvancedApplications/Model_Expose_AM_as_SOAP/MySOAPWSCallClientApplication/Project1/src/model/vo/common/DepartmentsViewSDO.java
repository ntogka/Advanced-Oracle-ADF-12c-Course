
package model.vo.common;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DepartmentsViewSDO complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DepartmentsViewSDO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DepartmentId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ManagerId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="LocationId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepartmentsViewSDO", propOrder = { "departmentId", "departmentName", "managerId", "locationId" })
public class DepartmentsViewSDO
{

  @XmlElement(name = "DepartmentId")
  protected Integer departmentId;
  @XmlElement(name = "DepartmentName")
  protected String departmentName;
  @XmlElementRef(name = "ManagerId", namespace = "/model/vo/common/", type = JAXBElement.class, required = false)
  protected JAXBElement<Integer> managerId;
  @XmlElementRef(name = "LocationId", namespace = "/model/vo/common/", type = JAXBElement.class, required = false)
  protected JAXBElement<Integer> locationId;

  /**
   * Gets the value of the departmentId property.
   *
   * @return
   *     possible object is
   *     {@link Integer }
   *
   */
  public Integer getDepartmentId()
  {
    return departmentId;
  }

  /**
   * Sets the value of the departmentId property.
   *
   * @param value
   *     allowed object is
   *     {@link Integer }
   *
   */
  public void setDepartmentId(Integer value)
  {
    this.departmentId = value;
  }

  /**
   * Gets the value of the departmentName property.
   *
   * @return
   *     possible object is
   *     {@link String }
   *
   */
  public String getDepartmentName()
  {
    return departmentName;
  }

  /**
   * Sets the value of the departmentName property.
   *
   * @param value
   *     allowed object is
   *     {@link String }
   *
   */
  public void setDepartmentName(String value)
  {
    this.departmentName = value;
  }

  /**
   * Gets the value of the managerId property.
   *
   * @return
   *     possible object is
   *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
   *
   */
  public JAXBElement<Integer> getManagerId()
  {
    return managerId;
  }

  /**
   * Sets the value of the managerId property.
   *
   * @param value
   *     allowed object is
   *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
   *
   */
  public void setManagerId(JAXBElement<Integer> value)
  {
    this.managerId = value;
  }

  /**
   * Gets the value of the locationId property.
   *
   * @return
   *     possible object is
   *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
   *
   */
  public JAXBElement<Integer> getLocationId()
  {
    return locationId;
  }

  /**
   * Sets the value of the locationId property.
   *
   * @param value
   *     allowed object is
   *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
   *
   */
  public void setLocationId(JAXBElement<Integer> value)
  {
    this.locationId = value;
  }

}
