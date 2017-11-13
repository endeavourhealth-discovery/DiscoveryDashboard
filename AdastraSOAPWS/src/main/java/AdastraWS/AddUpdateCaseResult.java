
package AdastraWS;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddUpdateCaseResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddUpdateCaseResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ResultDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddUpdateCaseResult", namespace = "http://tempuri.org/", propOrder = {
    "success",
    "resultDetails"
})
public class AddUpdateCaseResult {

    @XmlElement(name = "Success")
    protected boolean success;
    @XmlElement(name = "ResultDetails")
    protected String resultDetails;

    /**
     * Gets the value of the success property.
     * 
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     */
    public void setSuccess(boolean value) {
        this.success = value;
    }

    /**
     * Gets the value of the resultDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultDetails() {
        return resultDetails;
    }

    /**
     * Sets the value of the resultDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultDetails(String value) {
        this.resultDetails = value;
    }

}
