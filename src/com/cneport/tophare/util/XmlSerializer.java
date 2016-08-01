package com.cneport.tophare.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/*****************************************
 * Copyright (c) 2011, 东方口岸科技有限公司
 * All rights reserved
 *
 * 文 件 名 :XmlSerializer.java
 * 摘   要 : 
 * 版   本 : 
 * 作   者 : 
 * 创建日期 : 2011-12-8
 *****************************************/
public class XmlSerializer {

	public static String serialize(Object obj) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = jc.createMarshaller();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		marshaller.marshal(obj, stream);
		return new String(stream.toByteArray(), "UTF-8");
	}

	public static Element serializeToElement(Object obj) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = jc.createMarshaller();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		marshaller.marshal(obj, doc);
		return doc.getDocumentElement();
	}

	public static Object deSerialize(String str, Class<?> clazz)
			throws Exception {
		return deSerialize(str, clazz, "UTF-8");
	}

	public static Object deSerialize(String str, Class<?> clazz, String encoding)
			throws Exception {
		JAXBContext jc = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		byte[] bytes = str.getBytes(encoding);
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		return unmarshaller.unmarshal(stream);
	}

	public static Element deSerializeToElement(String str, String encoding)
			throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		byte[] bytes = str.getBytes(encoding);
		ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
		Document doc = db.parse(stream);
		return doc.getDocumentElement();
	}

	public static String deSerialize(Element element) throws Exception {
		TransformerFactory tfc = TransformerFactory.newInstance();
		Transformer tf = tfc.newTransformer();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		tf.transform(new DOMSource(element), new StreamResult(bos));
		String result = bos.toString("UTF-8");
		bos.close();
		return result;
	}

	public static Object getFile(Class<?> clazz, String path) throws Exception {
		InputStream is = new FileInputStream(path);
		JAXBContext jc = JAXBContext.newInstance(clazz);
		Unmarshaller u = jc.createUnmarshaller();
		return u.unmarshal(is);
	}

	/**
	 * @Name:formatXML
	 * @Description:XML格式化
	 * @Author:
	 * @Version:V1.0
	 * @Create Date:2011-12-8
	 * @Paramters:xml
	 * @Return:String
	 * @throws Exception
	 */
	public String formatXML(String xml) throws Exception {
		Document document = parseXmlFile(xml);
		OutputFormat format = new OutputFormat(document);
		format.setLineWidth(65);
		format.setIndent(2);
		Writer out = new StringWriter();
		XMLSerializer s = new XMLSerializer(out, format);
		s.serialize(document);
		return out.toString();
	}

	private Document parseXmlFile(String in) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(in));
		return builder.parse(is);
	}
}