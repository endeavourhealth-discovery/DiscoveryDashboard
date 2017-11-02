
package adastraWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddUpdateCaseResult" type="{http://tempuri.org/}AddUpdateCaseResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addUpdateCaseResult"
})
@XmlRootElement(name = "AddUpdateCaseResponse")
public class AddUpdateCaseResponse {

    @XmlElement(name = "AddUpdateCaseResult")
    protected AddUpdateCaseResult addUpdateCaseResult;

    /**
     * Gets the value of the addUpdateCaseResult property.
     * 
     * @return
     *     possible object is
     *     {@link AddUpdateCaseResult }
     *     
     */
    public AddUpdateCaseResult getAddUpdateCaseResult() {
        return addUpdateCaseResult;
    }

    /**
     * Sets the value of the addUpdateCaseResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddUpdateCaseResult }
     *     
     */
    public void setAddUpdateCaseResult(AddUpdateCaseResult value) {
        this.addUpdateCaseResult = value;
    }

}
