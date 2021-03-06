//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.01.26 at 02:04:22 PM MST 
//


package net.opengis.gml._311;

import java.math.BigInteger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Abstract element which acts as the head of a substitution group for coverages. Note that a coverage is a GML feature.
 * 
 * <p>Java class for AbstractCoverageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AbstractCoverageType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractFeatureType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}domainSet"/>
 *         &lt;element ref="{http://www.opengis.net/gml}rangeSet"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dimension" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractCoverageType", propOrder = {
    "domainSet",
    "rangeSet"
})
@XmlSeeAlso({
    AbstractContinuousCoverageType.class,
    AbstractDiscreteCoverageType.class
})
public abstract class AbstractCoverageType
    extends AbstractFeatureType
{

    @XmlElementRef(name = "domainSet", namespace = "http://www.opengis.net/gml", type = JAXBElement.class)
    protected JAXBElement<? extends DomainSetType> domainSet;
    @XmlElement(required = true)
    protected RangeSetType rangeSet;
    @XmlAttribute(name = "dimension")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger dimension;

    /**
     * Gets the value of the domainSet property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link DomainSetType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiSolidDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link RectifiedGridDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiCurveDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiSurfaceDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GridDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiPointDomainType }{@code >}
     *     
     */
    public JAXBElement<? extends DomainSetType> getDomainSet() {
        return domainSet;
    }

    /**
     * Sets the value of the domainSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link DomainSetType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiSolidDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link RectifiedGridDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiCurveDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiSurfaceDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link GridDomainType }{@code >}
     *     {@link JAXBElement }{@code <}{@link MultiPointDomainType }{@code >}
     *     
     */
    public void setDomainSet(JAXBElement<? extends DomainSetType> value) {
        this.domainSet = ((JAXBElement<? extends DomainSetType> ) value);
    }

    /**
     * Gets the value of the rangeSet property.
     * 
     * @return
     *     possible object is
     *     {@link RangeSetType }
     *     
     */
    public RangeSetType getRangeSet() {
        return rangeSet;
    }

    /**
     * Sets the value of the rangeSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link RangeSetType }
     *     
     */
    public void setRangeSet(RangeSetType value) {
        this.rangeSet = value;
    }

    /**
     * Gets the value of the dimension property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDimension() {
        return dimension;
    }

    /**
     * Sets the value of the dimension property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDimension(BigInteger value) {
        this.dimension = value;
    }

}
