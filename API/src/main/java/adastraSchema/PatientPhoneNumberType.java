
package adastraSchema;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patientPhoneNumberType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patientPhoneNumberType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="number">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="15"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="extension" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="6"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="numberType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Home"/>
 *             &lt;enumeration value="Work"/>
 *             &lt;enumeration value="Mobile"/>
 *             &lt;enumeration value="Other"/>
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
@XmlType(name = "patientPhoneNumberType", namespace = "http://www.adastra.com/dataExport", propOrder = {
    "number",
    "extension"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class PatientPhoneNumberType {

    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String number;
    @XmlElement(namespace = "http://www.adastra.com/dataExport")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String extension;
    @XmlAttribute(name = "numberType", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String numberType;

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
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the numberType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getNumberType() {
        return numberType;
    }

    /**
     * Sets the value of the numberType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setNumberType(String value) {
        this.numberType = value;
    }

}
