//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.22 at 05:04:32 ���� CST 
//


package com.cneport.ecss.exchange.service.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ��Ʒ������ͷ
 * 
 * <p>Java class for GoodsHead complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GoodsHead">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="seqNo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="36"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cbeCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="cbeName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="applyCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="applyName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ieType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="applyUser">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="60"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="customsCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="status">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="approveMan">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="60"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="approveTime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="approveOpinion">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1024"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="decTime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="updateTime">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="companyCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GoodsHead", propOrder = {
    "seqNo",
    "cbeCode",
    "cbeName",
    "applyCode",
    "applyName",
    "ieType",
    "applyUser",
    "customsCode",
    "status",
    "approveMan",
    "approveTime",
    "approveOpinion",
    "decTime",
    "updateTime",
    "companyCode"
})
public class GoodsHead {

    @XmlElement(required = true)
    protected String seqNo;
    @XmlElement(required = true)
    protected String cbeCode;
    @XmlElement(required = true)
    protected String cbeName;
    @XmlElement(required = true)
    protected String applyCode;
    @XmlElement(required = true)
    protected String applyName;
    @XmlElement(required = true)
    protected String ieType;
    @XmlElement(required = true)
    protected String applyUser;
    @XmlElement(required = true)
    protected String customsCode;//="4611";
    @XmlElement(required = true)
    protected String status;
    @XmlElement(required = true)
    protected String approveMan;
    @XmlElement(required = true)
    protected String approveTime;
    @XmlElement(required = true)
    protected String approveOpinion;
    @XmlElement(required = true)
    protected String decTime;
    @XmlElement(required = true)
    protected String updateTime;
    @XmlElement(required = true)
    protected String companyCode;

    /**
     * Gets the value of the seqNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * Sets the value of the seqNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeqNo(String value) {
        this.seqNo = value;
    }

    /**
     * Gets the value of the cbeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCbeCode() {
        return cbeCode;
    }

    /**
     * Sets the value of the cbeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCbeCode(String value) {
        this.cbeCode = value;
    }

    /**
     * Gets the value of the cbeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCbeName() {
        return cbeName;
    }

    /**
     * Sets the value of the cbeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCbeName(String value) {
        this.cbeName = value;
    }

    /**
     * Gets the value of the applyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyCode() {
        return applyCode;
    }

    /**
     * Sets the value of the applyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyCode(String value) {
        this.applyCode = value;
    }

    /**
     * Gets the value of the applyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyName() {
        return applyName;
    }

    /**
     * Sets the value of the applyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyName(String value) {
        this.applyName = value;
    }

    /**
     * Gets the value of the ieType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIeType() {
        return ieType;
    }

    /**
     * Sets the value of the ieType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIeType(String value) {
        this.ieType = value;
    }

    /**
     * Gets the value of the applyUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplyUser() {
        return applyUser;
    }

    /**
     * Sets the value of the applyUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplyUser(String value) {
        this.applyUser = value;
    }

    /**
     * Gets the value of the customsCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomsCode() {
        return customsCode;
    }

    /**
     * Sets the value of the customsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomsCode(String value) {
        this.customsCode = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the approveMan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveMan() {
        return approveMan;
    }

    /**
     * Sets the value of the approveMan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveMan(String value) {
        this.approveMan = value;
    }

    /**
     * Gets the value of the approveTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveTime() {
        return approveTime;
    }

    /**
     * Sets the value of the approveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveTime(String value) {
        this.approveTime = value;
    }

    /**
     * Gets the value of the approveOpinion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApproveOpinion() {
        return approveOpinion;
    }

    /**
     * Sets the value of the approveOpinion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApproveOpinion(String value) {
        this.approveOpinion = value;
    }

    /**
     * Gets the value of the decTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecTime() {
        return decTime;
    }

    /**
     * Sets the value of the decTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecTime(String value) {
        this.decTime = value;
    }

    /**
     * Gets the value of the updateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * Sets the value of the updateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateTime(String value) {
        this.updateTime = value;
    }

    /**
     * Gets the value of the companyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Sets the value of the companyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
    }

}