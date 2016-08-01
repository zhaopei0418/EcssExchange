/**
 * 
 */
package com.cneport.tophare.integration.config;

import java.io.File;
/**
 * @author mayujian
 *
 */
public class FileParameter {
	private  String sendBackupFolder;
	private  String receiveBackupFolder;
	private  String sendFolder;
	private  String sendFolder1;
	private  String receiveFolder;
	private  String errorFolder ="error";
	private  String dataFormat = "yyyyMMdd";
	
	public  String getSendBackupDirectoryDaily(){
		return getSendBackupFolder() +File.separator + new java.text.SimpleDateFormat(dataFormat).format(new java.util.Date());
	}
	public  String getReceiveBackupDirectoryDaily(){
		return getReceiveBackupFolder() +File.separator + new java.text.SimpleDateFormat(dataFormat).format(new java.util.Date());
	}
	public  String getReceiveErrorDirectoryDaily(){
		return getReceiveBackupDirectoryDaily() +File.separator +getErrorFolder();
	}
	public  String getSendErrorDirectoryDaily(){
		return getSendBackupDirectoryDaily() +File.separator+getErrorFolder();
	}
	
	
	
	
	
	
	public String getSendBackupFolder() {
		return sendBackupFolder;
	}
	public void setSendBackupFolder(String sendBackupFolder) {
		this.sendBackupFolder = sendBackupFolder;
	}
	public String getReceiveBackupFolder() {
		return receiveBackupFolder;
	}
	public void setReceiveBackupFolder(String receiveBackupFolder) {
		this.receiveBackupFolder = receiveBackupFolder;
	}
	public String getSendFolder() {
		return sendFolder;
	}
	public void setSendFolder(String sendFolder) {
		this.sendFolder = sendFolder;
	}
	public String getSendFolder1() {
		return sendFolder1;
	}
	public void setSendFolder1(String sendFolder1) {
		this.sendFolder1 = sendFolder1;
	}
	public String getReceiveFolder() {
		return receiveFolder;
	}
	public void setReceiveFolder(String receiveFolder) {
		this.receiveFolder = receiveFolder;
	}
	public String getErrorFolder() {
		return errorFolder;
	}
	public void setErrorFolder(String errorFolder) {
		this.errorFolder = errorFolder;
	}
	
	public String getDataFormat() {
		return dataFormat;
	}
	
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
}
