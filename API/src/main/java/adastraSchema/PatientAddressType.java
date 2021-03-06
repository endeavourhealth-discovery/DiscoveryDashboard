
package adastraSchema;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patientAddressType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patientAddressType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="line1">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="55"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="line2">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="55"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="locality">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="55"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="town">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="55"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="county">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="55"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="postcode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="15"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="addressType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Home"/>
 *             &lt;enumeration value="CurrentLocation"/>
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
@XmlType(name = "patientAddressType", namespace = "http://www.adastra.com/dataExport", propOrder = {
    "line1",
    "line2",
    "locality",
    "town",
    "county",
    "postcode"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class PatientAddressType {

    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String line1;
    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String line2;
    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String locality;
    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String town;
    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String county;
    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String postcode;
    @XmlAttribute(name = "addressType", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String addressType;

    /**
     * Gets the value of the line1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLine1() {
        return line1;
    }

    /**
     * Sets the value of the line1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLine1(String value) {
        this.line1 = value;
    }

    /**
     * Gets the value of the line2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLine2() {
        return line2;
    }

    /**
     * Sets the value of the line2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLine2(String value) {
        this.line2 = value;
    }

    /**
     * Gets the value of the locality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getLocality() {
        return locality;
    }

    /**
     * Sets the value of the locality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setLocality(String value) {
        this.locality = value;
    }

    /**
     * Gets the value of the town property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getTown() {
        return town;
    }

    /**
     * Sets the value of the town property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setTown(String value) {
        this.town = value;
    }

    /**
     * Gets the value of the county property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setCounty(String value) {
        this.county = value;
    }

    /**
     * Gets the value of the postcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the value of the postcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setPostcode(String value) {
        this.postcode = value;
    }

    /**
     * Gets the value of the addressType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getAddressType() {
        return addressType;
    }

    /**
     * Sets the value of the addressType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setAddressType(String value) {
        this.addressType = value;
    }

}
