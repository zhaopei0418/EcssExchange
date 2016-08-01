package com.cneport.ecss.exchange.service.payInfo.entity;

public class PayInfo
{
  private String seqNo;
  private String PPaymentCode;
  private String PPaymentName;
  private String PPaymentType;
  private String PPaymentNo;
  private String PCharge;
  private String PCurrency;
  private String PCustomerFlag;
  private String PCustomerData;
  private String PNote;
  private String PIEType;

  public String getSeqNo()
  {
    return this.seqNo;
  }
  public void setSeqNo(String seqNo) {
    this.seqNo = seqNo;
  }
  public String getPPaymentCode() {
    return this.PPaymentCode;
  }
  public void setPPaymentCode(String pPaymentCode) {
    this.PPaymentCode = pPaymentCode;
  }
  public String getPPaymentName() {
    return this.PPaymentName;
  }
  public void setPPaymentName(String pPaymentName) {
    this.PPaymentName = pPaymentName;
  }
  public String getPPaymentType() {
    return this.PPaymentType;
  }
  public void setPPaymentType(String pPaymentType) {
    this.PPaymentType = pPaymentType;
  }
  public String getPPaymentNo() {
    return this.PPaymentNo;
  }
  public void setPPaymentNo(String pPaymentNo) {
    this.PPaymentNo = pPaymentNo;
  }
  public String getPCharge() {
    return this.PCharge;
  }
  public void setPCharge(String pCharge) {
    this.PCharge = pCharge;
  }
  public String getPCurrency() {
    return this.PCurrency;
  }
  public void setPCurrency(String pCurrency) {
    this.PCurrency = pCurrency;
  }
  public String getPCustomerFlag() {
    return this.PCustomerFlag;
  }
  public void setPCustomerFlag(String pCustomerFlag) {
    this.PCustomerFlag = pCustomerFlag;
  }
  public String getPCustomerData() {
    return this.PCustomerData;
  }
  public void setPCustomerData(String pCustomerData) {
    this.PCustomerData = pCustomerData;
  }
  public String getPNote() {
    return this.PNote;
  }
  public void setPNote(String pNote) {
    this.PNote = pNote;
  }
  public String getPIEType() {
    return this.PIEType;
  }
  public void setPIEType(String pIEType) {
    this.PIEType = pIEType;
  }
}