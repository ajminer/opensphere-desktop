//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.30 at 02:21:16 PM MDT 
//


package com.bitsys.fade.mist.state.v4;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *         Defines a constrained bin.
 *       
 * 
 * <p>Java class for BinSettingsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BinSettingsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="active" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="binSize" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="bins" type="{http://www.bit-sys.com/mist/state/v4}BinMethodSequenceType" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinSettingsType", propOrder = {

})
public class BinSettingsType {

    protected Boolean active;
    @XmlElement(required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger binSize;
    protected BinMethodSequenceType bins;

    /**
     * Gets the value of the active property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Sets the value of the active property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActive(Boolean value) {
        this.active = value;
    }

    public boolean isSetActive() {
        return (this.active!= null);
    }

    /**
     * Gets the value of the binSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBinSize() {
        return binSize;
    }

    /**
     * Sets the value of the binSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBinSize(BigInteger value) {
        this.binSize = value;
    }

    public boolean isSetBinSize() {
        return (this.binSize!= null);
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link BinMethodSequenceType }
     *     
     */
    public BinMethodSequenceType getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link BinMethodSequenceType }
     *     
     */
    public void setBins(BinMethodSequenceType value) {
        this.bins = value;
    }

    public boolean isSetBins() {
        return (this.bins!= null);
    }

}
