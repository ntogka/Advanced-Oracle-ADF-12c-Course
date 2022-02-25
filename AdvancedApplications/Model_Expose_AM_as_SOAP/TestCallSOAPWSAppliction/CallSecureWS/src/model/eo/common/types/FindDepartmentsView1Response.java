
package model.eo.common.types;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="result" type="{/model/vo/common/}DepartmentsViewSDO" maxOccurs="unbounded" minOccurs="0"/&gt;
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
@XmlRootElement(name = "findDepartmentsView1Response")
public class FindDepartmentsView1Response
{

  protected List<DepartmentsViewSDO> result;

  /**
   * Gets the value of the result property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the result property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getResult().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link DepartmentsViewSDO }
   *
   *
   */
  public List<DepartmentsViewSDO> getResult()
  {
    if (result == null)
    {
      result = new ArrayList<DepartmentsViewSDO>();
    }
    return this.result;
  }

}
