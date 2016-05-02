//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.02 at 05:26:25 下午 CST 
//


package uia.message.model.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataExType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataExType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BlockSpace" type="{http://message.uia/model/xml}BlockSpaceType"/>
 *         &lt;element name="MessageSpace" type="{http://message.uia/model/xml}MessageSpaceType"/>
 *         &lt;element name="BlockCodecSpace" type="{http://message.uia/model/xml}BlockCodecSpaceType"/>
 *         &lt;element name="FxSpace" type="{http://message.uia/model/xml}FxSpaceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataExType", propOrder = {
    "blockSpace",
    "messageSpace",
    "blockCodecSpace",
    "fxSpace"
})
public class DataExType {

    @XmlElement(name = "BlockSpace", required = true)
    protected BlockSpaceType blockSpace;
    @XmlElement(name = "MessageSpace", required = true)
    protected MessageSpaceType messageSpace;
    @XmlElement(name = "BlockCodecSpace", required = true)
    protected BlockCodecSpaceType blockCodecSpace;
    @XmlElement(name = "FxSpace", required = true)
    protected FxSpaceType fxSpace;

    /**
     * Gets the value of the blockSpace property.
     * 
     * @return
     *     possible object is
     *     {@link BlockSpaceType }
     *     
     */
    public BlockSpaceType getBlockSpace() {
        return blockSpace;
    }

    /**
     * Sets the value of the blockSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlockSpaceType }
     *     
     */
    public void setBlockSpace(BlockSpaceType value) {
        this.blockSpace = value;
    }

    /**
     * Gets the value of the messageSpace property.
     * 
     * @return
     *     possible object is
     *     {@link MessageSpaceType }
     *     
     */
    public MessageSpaceType getMessageSpace() {
        return messageSpace;
    }

    /**
     * Sets the value of the messageSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageSpaceType }
     *     
     */
    public void setMessageSpace(MessageSpaceType value) {
        this.messageSpace = value;
    }

    /**
     * Gets the value of the blockCodecSpace property.
     * 
     * @return
     *     possible object is
     *     {@link BlockCodecSpaceType }
     *     
     */
    public BlockCodecSpaceType getBlockCodecSpace() {
        return blockCodecSpace;
    }

    /**
     * Sets the value of the blockCodecSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlockCodecSpaceType }
     *     
     */
    public void setBlockCodecSpace(BlockCodecSpaceType value) {
        this.blockCodecSpace = value;
    }

    /**
     * Gets the value of the fxSpace property.
     * 
     * @return
     *     possible object is
     *     {@link FxSpaceType }
     *     
     */
    public FxSpaceType getFxSpace() {
        return fxSpace;
    }

    /**
     * Sets the value of the fxSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link FxSpaceType }
     *     
     */
    public void setFxSpace(FxSpaceType value) {
        this.fxSpace = value;
    }

}
