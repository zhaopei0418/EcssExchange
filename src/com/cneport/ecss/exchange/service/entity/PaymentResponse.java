//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.10.28 at 05:18:17 ���� CST 
//

package com.cneport.ecss.exchange.service.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PaymentInformation"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "paymentInformation" })
@XmlRootElement(name = "PaymentResponse")
public class PaymentResponse {

    @XmlElement(name = "PaymentInformation", required = true)
    protected PaymentInformation paymentInformation;

    /**
     * Gets the value of the paymentInformation property.
     * 
     * @return possible object is {@link PaymentInformation }
     * 
     */
    public PaymentInformation getPaymentInformation() {
	return paymentInformation;
    }

    /**
     * Sets the value of the paymentInformation property.
     * 
     * @param value
     *            allowed object is {@link PaymentInformation }
     * 
     */
    public void setPaymentInformation(PaymentInformation value) {
	this.paymentInformation = value;
    }

}
