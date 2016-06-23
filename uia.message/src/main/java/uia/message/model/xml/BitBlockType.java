//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.23 at 09:04:31 AM CST 
//


package uia.message.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BitBlockType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BitBlockType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://message.uia/model/xml}BlockBaseType">
 *       &lt;sequence>
 *         &lt;element name="CodecPropSet" type="{http://message.uia/model/xml}CodecPropSetType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dataType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sizeUnit" type="{http://www.w3.org/2001/XMLSchema}string" default="byte" />
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="sizeBlock" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="sizeFx" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="readonly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BitBlockType", propOrder = {
    "codecPropSet"
})
@XmlSeeAlso({
    BitBlockListType.class
})
public class BitBlockType
    extends BlockBaseType
{

    @XmlElement(name = "CodecPropSet", required = true)
    protected CodecPropSetType codecPropSet;
    @XmlAttribute(required = true)
    protected String dataType;
    @XmlAttribute
    protected String sizeUnit;
    @XmlAttribute
    protected Integer size;
    @XmlAttribute
    protected String sizeBlock;
    @XmlAttribute
    protected String sizeFx;
    @XmlAttribute
    protected Boolean readonly;

    /**
     * Gets the value of the codecPropSet property.
     * 
     * @return
     *     possible object is
     *     {@link CodecPropSetType }
     *     
     */
    public CodecPropSetType getCodecPropSet() {
        return codecPropSet;
    }

    /**
     * Sets the value of the codecPropSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodecPropSetType }
     *     
     */
    public void setCodecPropSet(CodecPropSetType value) {
        this.codecPropSet = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(String value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the sizeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSizeUnit() {
        if (sizeUnit == null) {
            return "byte";
        } else {
            return sizeUnit;
        }
    }

    /**
     * Sets the value of the sizeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSizeUnit(String value) {
        this.sizeUnit = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSize() {
        if (size == null) {
            return  1;
        } else {
            return size;
        }
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSize(Integer value) {
        this.size = value;
    }

    /**
     * Gets the value of the sizeBlock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSizeBlock() {
        return sizeBlock;
    }

    /**
     * Sets the value of the sizeBlock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSizeBlock(String value) {
        this.sizeBlock = value;
    }

    /**
     * Gets the value of the sizeFx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSizeFx() {
        return sizeFx;
    }

    /**
     * Sets the value of the sizeFx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSizeFx(String value) {
        this.sizeFx = value;
    }

    /**
     * Gets the value of the readonly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isReadonly() {
        if (readonly == null) {
            return false;
        } else {
            return readonly;
        }
    }

    /**
     * Sets the value of the readonly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadonly(Boolean value) {
        this.readonly = value;
    }

}
