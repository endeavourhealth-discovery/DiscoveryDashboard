
package adastraSchema;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nationalNumberType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nationalNumberType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="number">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="nationalNumberStatus" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Confirmed"/>
 *             &lt;enumeration value="Unconfirmed"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nationalNumberType", namespace = "http://www.adastra.com/dataExport", propOrder = {
    "number"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class NationalNumberType {

    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String number;
    @XmlAttribute(name = "nationalNumberStatus", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String nationalNumberStatus;

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the nationalNumberStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNationalNumberStatus() {
        return nationalNumberStatus;
    }

    /**
     * Sets the value of the nationalNumberStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNationalNumberStatus(String value) {
        this.nationalNumberStatus = value;
    }

}
