
package adastraSchema;

import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for dateOfBirthType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dateOfBirthType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dobValue" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dataOfBirthType" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="Exact"/>
 *             &lt;enumeration value="AgeOnly"/>
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
@XmlType(name = "dateOfBirthType", namespace = "http://www.adastra.com/dataExport", propOrder = {
    "dobValue"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
public class DateOfBirthType {

    @XmlElement(namespace = "http://www.adastra.com/dataExport", required = true)
    @XmlSchemaType(name = "date")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected XMLGregorianCalendar dobValue;
    @XmlAttribute(name = "dataOfBirthType", required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    protected String dataOfBirthType;

    /**
     * Gets the value of the dobValue property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public XMLGregorianCalendar getDobValue() {
        return dobValue;
    }

    /**
     * Sets the value of the dobValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDobValue(XMLGregorianCalendar value) {
        this.dobValue = value;
    }

    /**
     * Gets the value of the dataOfBirthType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public String getDataOfBirthType() {
        return dataOfBirthType;
    }

    /**
     * Sets the value of the dataOfBirthType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2017-11-02T11:39:01+00:00", comments = "JAXB RI v2.2.8-b130911.1802")
    public void setDataOfBirthType(String value) {
        this.dataOfBirthType = value;
    }

}
