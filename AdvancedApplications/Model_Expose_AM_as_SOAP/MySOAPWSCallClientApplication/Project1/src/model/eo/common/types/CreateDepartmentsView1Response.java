
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
 *         &lt;element name="result" type="{/model/vo/common/}DepartmentsViewSDO"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "result" })
@XmlRootElement(name = "createDepartmentsView1Response")
public class CreateDepartmentsView1Response
{

  @XmlElement(required = true)
  protected DepartmentsViewSDO result;

  /**
   * Gets the value of the result property.
   *
   * @return
   *     possible object is
   *     {@link DepartmentsViewSDO }
   *
   */
  public DepartmentsViewSDO getResult()
  {
    return result;
  }

  /**
   * Sets the value of the result property.
   *
   * @param value
   *     allowed object is
   *     {@link DepartmentsViewSDO }
   *
   */
  public void setResult(DepartmentsViewSDO value)
  {
    this.result = value;
  }

}
